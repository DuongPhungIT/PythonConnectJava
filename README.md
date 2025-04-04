# Python-Java gRPC Image Comparison Application

This application demonstrates how to use gRPC to connect a Java Spring Boot application to a Python server for face comparison using DeepFace.

## Project Structure

- `python_server/`: Contains the Python gRPC server that uses DeepFace for image comparison
- `java_project/`: Contains the Java Spring Boot client application

## Prerequisites

- Java 11 or higher
- Python 3.8 or higher
- Maven
- pip (Python package manager)

## Setup

### Python Server

1. Navigate to the Python server directory:
   ```
   cd python_server
   ```

2. Install the required Python packages:
   ```
   pip install -r requirements.txt
   ```

3. Start the Python gRPC server:
   ```
   python server.py
   ```
   The server will start on port 50051.

### Java Client

1. Navigate to the Java project directory:
   ```
   cd java_project
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```
   The application will start on port 8081.

## Usage

1. Open a web browser and navigate to `http://localhost:8081`
2. You'll see two options:
   - Calculator: A simple calculator that demonstrates basic gRPC communication
   - Face Comparison: Upload two images to compare faces and check authenticity

3. For face comparison:
   - Click on "Face Comparison" in the navigation
   - Upload two images using the file inputs
   - Click "Compare Images"
   - View the results showing whether the images are of the same person and if they appear to be real or fake

## Convenience Scripts

The project includes several convenience scripts to help with common tasks:

### Starting the Application

To start both the Python server and Java client with a single command:

```
./start_app.sh
```

This script will:
1. Check for required dependencies
2. Create the uploads directory if it doesn't exist
3. Start the Python gRPC server in the background
4. Start the Java Spring Boot application
5. Automatically stop the Python server when the Java application is stopped

### Running Tests

To run all tests (both Python and Java):

```
./run_tests.sh
```

This script will:
1. Start the Python gRPC server in the background
2. Run the Python tests
3. Run the Java tests
4. Stop the Python server
5. Display a summary of test results

### Cleaning Up

To clean up temporary files and build artifacts:

```
./clean.sh
```

This script will:
1. Clean Java build artifacts using Maven
2. Remove Python cache files
3. Clean the uploads directory
4. Remove any temporary files

## Testing

To run the tests:

```
cd java_project
mvn test
```

Note: The image comparison test requires the Python server to be running and test images to be available in the uploads directory.

## Troubleshooting

- If you encounter connection issues, ensure both the Python server and Java client are running
- Check that the ports (50051 for Python server, 8081 for Java client) are not in use by other applications
- Verify that the uploads directory exists and has write permissions
- For image comparison issues, ensure the images are in a supported format (JPEG, PNG)
