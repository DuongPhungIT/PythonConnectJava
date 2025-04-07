import grpc
from concurrent import futures
import calculator_pb2
import calculator_pb2_grpc
import logging
import os
import requests
import tempfile
from deepface import DeepFace
from urllib.parse import urlparse
import time
import json
import socket
import platform
from logging.handlers import TimedRotatingFileHandler

# Tạo thư mục logs nếu chưa tồn tại
log_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'logs')
os.makedirs(log_dir, exist_ok=True)

# Cấu hình logging
class CustomFormatter(logging.Formatter):
    def format(self, record):
        # Lấy thông tin hệ thống
        hostname = socket.gethostname()
        process_id = os.getpid()
        timestamp = time.strftime('%Y-%m-%d %H:%M:%S,%f')[:-3]
        date_time = time.strftime('%d-%m-%Y %H:%M:%S')
        
        # Tạo message với định dạng yêu cầu
        message = (
            f"{timestamp} - {record.levelname} "
            f"[{hostname}-{process_id}@LogUtils:{record.lineno}] - "
            f"{date_time}\t"
            f"{hostname}\t"
            f"{record.funcName}\t"
            f"{record.levelname}\t"
            f"{record.process}\t"
            f"{record.thread}\t"
            f"{platform.platform()}\t"
            f"{record.getMessage()}"
        )
        return message

# Cấu hình logger
logger = logging.getLogger('FaceComparison')
logger.setLevel(logging.INFO)

# Tạo handler cho console
console_handler = logging.StreamHandler()
console_formatter = CustomFormatter()
console_handler.setFormatter(console_formatter)
logger.addHandler(console_handler)

# Tạo handler cho file với rotation theo ngày
log_file = os.path.join(log_dir, 'face_comparison.log')
file_handler = TimedRotatingFileHandler(
    log_file,
    when='midnight',
    interval=1,
    backupCount=30,  # Giữ 30 ngày
    encoding='utf-8'
)
file_formatter = CustomFormatter()
file_handler.setFormatter(file_formatter)
logger.addHandler(file_handler)

class CalculatorServicer(calculator_pb2_grpc.CalculatorServiceServicer):
    def __init__(self):
        logger.info("Server initialized")
        self.start_time = time.time()

    def add(self, request, context):
        request_data = {"a": request.a, "b": request.b}
        result = request.a + request.b
        response_data = {"result": result}
        logger.info(f"Request: {json.dumps(request_data)} Response: {json.dumps(response_data)}")
        return calculator_pb2.AddResponse(result=result)
    
    def _download_image(self, url, temp_dir, image_name):
        try:
            response = requests.get(url, stream=True)
            response.raise_for_status()
            
            file_path = os.path.join(temp_dir, f"{image_name}.jpg")
            with open(file_path, 'wb') as f:
                for chunk in response.iter_content(chunk_size=8192):
                    f.write(chunk)
            return file_path
        except Exception as e:
            logger.error(f"Download failed: {str(e)}")
            raise
    
    def compareImages(self, request, context):
        request_id = str(int(time.time() * 1000))
        request_data = {
            'request_id': request_id,
            'image1': request.image1_path,
            'image2': request.image2_path
        }
        
        try:
            with tempfile.TemporaryDirectory() as temp_dir:
                # Tải ảnh
                image1_path = self._download_image(request.image1_path, temp_dir, "image1")
                image2_path = self._download_image(request.image2_path, temp_dir, "image2")
                
                # Kiểm tra ảnh 1
                image1_faces = DeepFace.extract_faces(
                    img_path=image1_path,
                    anti_spoofing=True,
                    detector_backend='opencv',
                    enforce_detection=False
                )
                image1_is_fake = not image1_faces[0]['is_real']

                # Kiểm tra ảnh 2
                image2_faces = DeepFace.extract_faces(
                    img_path=image2_path,
                    anti_spoofing=True,
                    detector_backend='opencv',
                    enforce_detection=False
                )
                image2_is_fake = not image2_faces[0]['is_real']

                is_same_person = False
                if not image1_is_fake and not image2_is_fake:
                    result = DeepFace.verify(
                        img1_path=image1_path,
                        img2_path=image2_path,
                        model_name="VGG-Face",
                        detector_backend="opencv",
                        enforce_detection=False
                    )
                    is_same_person = result["verified"]

                response_data = {
                    'request_id': request_id,
                    'is_same_person': is_same_person,
                    'image1_is_fake': image1_is_fake,
                    'image2_is_fake': image2_is_fake
                }
                
                logger.info(f"Request: {json.dumps(request_data)} Response: {json.dumps(response_data)}")
                
                return calculator_pb2.ImageComparisonResponse(
                    is_same_person=is_same_person,
                    image1_is_fake=image1_is_fake,
                    image2_is_fake=image2_is_fake
                )
        except Exception as e:
            error_data = {
                'request_id': request_id,
                'error': str(e),
                'type': type(e).__name__
            }
            logger.error(f"Request: {json.dumps(request_data)} Error: {json.dumps(error_data)}")
            context.set_code(grpc.StatusCode.INTERNAL)
            context.set_details(str(e))
            return calculator_pb2.ImageComparisonResponse()

def serve():
    logger.info("Server started")
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServiceServicer_to_server(CalculatorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    logger.info("Server listening on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve() 