import grpc
import calculator_pb2
import calculator_pb2_grpc
import os
import sys

def test_add():
    """Test the add function of the gRPC server"""
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = calculator_pb2_grpc.CalculatorServiceStub(channel)
        request = calculator_pb2.AddRequest(a=5.0, b=3.0)
        try:
            response = stub.add(request)
            print(f"Add test: 5.0 + 3.0 = {response.result}")
            assert response.result == 8.0, f"Expected 8.0, got {response.result}"
            print("Add test passed!")
        except grpc.RpcError as e:
            print(f"Add test failed: {e}")
            return False
    return True

def test_compare_images():
    """Test the compareImages function of the gRPC server"""
    # Create test images directory if it doesn't exist
    os.makedirs("../java_project/uploads", exist_ok=True)
    
    # Check if test images exist
    image1_path = "../java_project/uploads/test1.jpg"
    image2_path = "../java_project/uploads/test2.jpg"
    
    if not (os.path.exists(image1_path) and os.path.exists(image2_path)):
        print("Test images not found. Please place test1.jpg and test2.jpg in the java_project/uploads directory.")
        return False
    
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = calculator_pb2_grpc.CalculatorServiceStub(channel)
        request = calculator_pb2.ImageComparisonRequest(
            image1_path=image1_path,
            image2_path=image2_path
        )
        try:
            response = stub.compareImages(request)
            print(f"Compare images test results:")
            print(f"  Same person: {response.is_same_person}")
            print(f"  Image 1 is fake: {response.image1_is_fake}")
            print(f"  Image 2 is fake: {response.image2_is_fake}")
            print("Compare images test passed!")
        except grpc.RpcError as e:
            print(f"Compare images test failed: {e}")
            return False
    return True

if __name__ == "__main__":
    print("Testing gRPC server...")
    
    # Test add function
    add_success = test_add()
    
    # Test compare images function
    compare_success = test_compare_images()
    
    # Print summary
    print("\nTest Summary:")
    print(f"Add function: {'PASSED' if add_success else 'FAILED'}")
    print(f"Compare images function: {'PASSED' if compare_success else 'FAILED'}")
    
    # Exit with appropriate status code
    sys.exit(0 if (add_success and compare_success) else 1) 