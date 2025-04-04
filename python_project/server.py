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
    
    def _download_image(self, url_or_path):
        """Download image from URL or return local path"""
        try:
            if urlparse(url_or_path).scheme in ('http', 'https'):
                response = requests.get(url_or_path)
                response.raise_for_status()
                
                # Create temp file with .jpg extension
                temp = tempfile.NamedTemporaryFile(delete=False, suffix='.jpg')
                temp.write(response.content)
                temp.close()
                return temp.name
            else:
                if not os.path.exists(url_or_path):
                    raise FileNotFoundError(f"Image not found: {url_or_path}")
                return url_or_path
        except Exception as e:
            logger.error(f"Error downloading image: {str(e)}")
            raise
    
    def compareImages(self, request, context):
        logger.info(f"Comparing images: {request.image1_path} and {request.image2_path}")
        temp_files = []
        
        try:
            # Download or get local paths for both images
            img1_path = self._download_image(request.image1_path)
            img2_path = self._download_image(request.image2_path)
            
            if urlparse(request.image1_path).scheme in ('http', 'https'):
                temp_files.append(img1_path)
            if urlparse(request.image2_path).scheme in ('http', 'https'):
                temp_files.append(img2_path)
            
            # Compare images with more lenient face detection
            result = DeepFace.verify(
                img1_path=img1_path,
                img2_path=img2_path,
                model_name="VGG-Face",
                detector_backend="opencv",
                distance_metric="cosine",
                enforce_detection=False  # More lenient face detection
            )
            
            # Check image authenticity with anti-spoofing
            try:
                is_first_image_real = DeepFace.extract_faces(
                    img_path=img1_path,
                    anti_spoofing=True
                )
                first_real = any(face.get('is_real', False) for face in is_first_image_real) if is_first_image_real else False
            except Exception as e:
                logger.warning(f"Anti-spoofing check failed for image 1: {str(e)}")
                first_real = True  # Assume real if check fails
            
            try:
                is_secondary_image_real = DeepFace.extract_faces(
                    img_path=img2_path,
                    anti_spoofing=True
                )
                second_real = any(face.get('is_real', False) for face in is_secondary_image_real) if is_secondary_image_real else False
            except Exception as e:
                logger.warning(f"Anti-spoofing check failed for image 2: {str(e)}")
                second_real = True  # Assume real if check fails
            
            # Get comparison result
            is_same_person = result["verified"]
            
            logger.info(f"Comparison result: {is_same_person}")
            logger.info(f"Image 1 is real: {first_real}")
            logger.info(f"Image 2 is real: {second_real}")
            
            return calculator_pb2.ImageComparisonResponse(
                is_same_person=is_same_person,
                image1_is_fake=not first_real,
                image2_is_fake=not second_real
            )
            
        except Exception as e:
            logger.error(f"Error in compareImages: {str(e)}")
            context.set_code(grpc.StatusCode.INTERNAL)
            context.set_details(str(e))
            return calculator_pb2.ImageComparisonResponse()
            
        finally:
            # Clean up temporary files
            for temp_file in temp_files:
                try:
                    os.unlink(temp_file)
                except Exception as e:
                    logger.warning(f"Error cleaning up temp file {temp_file}: {str(e)}")

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServiceServicer_to_server(CalculatorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    logger.info("Server started on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve() 