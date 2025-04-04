import grpc
from concurrent import futures
import calculator_pb2
import calculator_pb2_grpc
import logging
import os
from deepface import DeepFace

# Cấu hình logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class CalculatorServicer(calculator_pb2_grpc.CalculatorServiceServicer):
    def add(self, request, context):
        # In ra các giá trị nhận được từ client
        logger.info(f"Received request from client:")
        logger.info(f"Number A: {request.a}")
        logger.info(f"Number B: {request.b}")
        
        # Thực hiện phép cộng
        result = request.a + request.b
        logger.info(f"Calculating: {request.a} + {request.b} = {result}")
        
        return calculator_pb2.AddResponse(result=result)
    
    def compareImages(self, request, context):
        logging.info(f"Received image comparison request: {request}")
        try:
            # Kiểm tra xem các file ảnh có tồn tại không
            if not os.path.exists(request.image1_path):
                context.set_code(grpc.StatusCode.NOT_FOUND)
                context.set_details(f"Image 1 not found: {request.image1_path}")
                return calculator_pb2.ImageComparisonResponse()
            
            if not os.path.exists(request.image2_path):
                context.set_code(grpc.StatusCode.NOT_FOUND)
                context.set_details(f"Image 2 not found: {request.image2_path}")
                return calculator_pb2.ImageComparisonResponse()
            
            # So sánh hai ảnh để xác định có phải cùng một người không
            result = DeepFace.verify(
                img1_path=request.image1_path,
                img2_path=request.image2_path,
                model_name="ArcFace",
                detector_backend="retinaface",
                distance_metric="cosine",
                enforce_detection=True
            )
            
            # Kiểm tra xem ảnh có phải là ảnh giả không bằng anti-spoofing
            is_first_image_real = DeepFace.extract_faces(
                img_path=request.image1_path, 
                anti_spoofing=True
            )
            
            is_secondary_image_real = DeepFace.extract_faces(
                img_path=request.image2_path, 
                anti_spoofing=True
            )
            
            # Kiểm tra kết quả anti-spoofing
            first_real = any(face.get('is_real', False) for face in is_first_image_real) if is_first_image_real else False
            second_real = any(face.get('is_real', False) for face in is_secondary_image_real) if is_secondary_image_real else False
            
            # Lấy kết quả so sánh
            is_same_person = result["verified"]
            
            logger.info(f"Comparison result: {is_same_person}")
            logger.info(f"Image 1 is real: {first_real}")
            logger.info(f"Image 2 is real: {second_real}")
            
            return calculator_pb2.ImageComparisonResponse(
                is_same_person=is_same_person,
                image1_is_fake=not first_real,  # Nếu không phải ảnh thật thì là ảnh giả
                image2_is_fake=not second_real,  # Nếu không phải ảnh thật thì là ảnh giả
            )
            
        except Exception as e:
            logger.error(f"Error in compareImages: {str(e)}")
            context.set_code(grpc.StatusCode.INTERNAL)
            context.set_details(str(e))
            return calculator_pb2.ImageComparisonResponse()

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServiceServicer_to_server(CalculatorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    logger.info("Server started on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve() 