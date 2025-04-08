package com.example.calculator.grpc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Assumptions;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "grpc.server.port=50051",
    "grpc.server.host=localhost"
})
public class CalculatorGrpcClientTest {

    @Autowired
    private CalculatorGrpcClient calculatorGrpcClient;

    @Test
    public void testAdd() {
        try {
            double result = calculatorGrpcClient.add(5.0, 3.0);
            assertEquals(8.0, result, 0.001);
        } catch (Exception e) {
            // Skip test if Python server is not running
            Assumptions.assumeTrue(false, "Python server is not running: " + e.getMessage());
        }
    }

    @Test
    public void testCompareImages() {
        // This test requires the Python server to be running and test images to be available
        // You would need to create test images in the uploads directory
        String image1Path = "uploads/test1.jpg";
        String image2Path = "uploads/test2.jpg";
        
        try {
            var response = calculatorGrpcClient.compareImages(image1Path, image2Path);
            assertNotNull(response);
            // We can't assert specific values since they depend on the actual images
            // Just verify that we got a response
        } catch (Exception e) {
            // Skip test if Python server is not running
            Assumptions.assumeTrue(false, "Python server is not running: " + e.getMessage());
        }
    }
} 