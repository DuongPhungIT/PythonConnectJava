import grpc
from concurrent import futures
import calculator_pb2
import calculator_pb2_grpc
import logging

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

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServiceServicer_to_server(CalculatorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    logger.info("Server started on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve() 