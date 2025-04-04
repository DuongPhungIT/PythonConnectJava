#!/bin/bash

echo "Cleaning up temporary files and build artifacts..."

# Clean Java build artifacts
echo "Cleaning Java build artifacts..."
cd java_project
mvn clean
cd ..

# Clean Python cache files
echo "Cleaning Python cache files..."
find . -type d -name "__pycache__" -exec rm -rf {} +
find . -type f -name "*.pyc" -delete

# Clean uploads directory (but keep the directory itself)
echo "Cleaning uploads directory..."
if [ -d "java_project/uploads" ]; then
    rm -f java_project/uploads/*
fi

# Clean any temporary files
echo "Cleaning temporary files..."
find . -type f -name "*.log" -delete
find . -type f -name "*.tmp" -delete

echo "Cleanup complete!" 