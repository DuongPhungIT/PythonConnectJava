#!/bin/bash

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check for required commands
if ! command_exists python3; then
    echo "Error: Python 3 is required but not installed."
    exit 1
fi

if ! command_exists mvn; then
    echo "Error: Maven is required but not installed."
    exit 1
fi

# Create uploads directory if it doesn't exist
mkdir -p java_project/uploads

# Start Python server in the background
echo "Starting Python gRPC server..."
cd python_server
python3 server.py &
PYTHON_PID=$!
cd ..

# Wait a moment for the Python server to start
sleep 2

# Start Java client
echo "Starting Java Spring Boot application..."
cd java_project
mvn spring-boot:run

# When the Java application is stopped, also stop the Python server
echo "Stopping Python gRPC server..."
kill $PYTHON_PID 