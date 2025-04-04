# Calculator gRPC Application

This is a simple calculator application that demonstrates gRPC communication between a Python server and a Java client.

## Project Structure

```
.
├── java_project/          # Java Spring Boot client
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── calculator/
│   │       │               ├── CalculatorApplication.java
│   │       │               ├── CalculatorController.java
│   │       │               └── grpc/
│   │       │                   ├── AddRequest.java
│   │       │                   ├── AddResponse.java
│   │       │                   ├── CalculatorGrpcClient.java
│   │       │                   ├── CalculatorProto.java
│   │       │                   └── CalculatorServiceGrpc.java
│   │       └── resources/
│   │           ├── application.properties
│   │           └── templates/
│   │               └── calculator.html
│   └── pom.xml
├── python_project/        # Python gRPC server
│   ├── server.py
│   └── requirements.txt
└── proto/                # Protocol buffer definitions
    └── calculator.proto
```

## Prerequisites

- Java 11 or later
- Python 3.7 or later
- Maven
- pip (Python package manager)
- protoc (Protocol Buffers compiler)

## Setup

1. Install Python dependencies:
   ```bash
   cd python_project
   pip install -r requirements.txt
   ```

2. Install Java dependencies:
   ```bash
   cd java_project
   mvn clean install
   ```

## Running the Application

1. Start the Python gRPC server:
   ```bash
   cd python_project
   python server.py
   ```

2. Start the Java Spring Boot application:
   ```bash
   cd java_project
   mvn spring-boot:run
   ```

3. Open your browser and navigate to:
   ```
   http://localhost:8081
   ```

## Features

- Simple calculator interface
- Addition operation using gRPC
- Real-time communication between client and server
- Clean and modern UI

## Technologies Used

- Java Spring Boot
- Python gRPC
- Protocol Buffers
- Thymeleaf
- HTML/CSS
