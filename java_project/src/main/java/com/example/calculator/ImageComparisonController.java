package com.example.calculator;

import com.example.calculator.grpc.CalculatorGrpcClient;
import com.example.calculator.grpc.ImageComparisonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/images")
public class ImageComparisonController {
    private static final Logger logger = LoggerFactory.getLogger(ImageComparisonController.class);
    
    private final CalculatorGrpcClient calculatorGrpcClient;

    @Autowired
    public ImageComparisonController(CalculatorGrpcClient calculatorGrpcClient) {
        this.calculatorGrpcClient = calculatorGrpcClient;
    }

    @PostMapping("/compare")
    public ResponseEntity<ImageComparisonResponse> compareImages(
            @RequestParam String image1Path,
            @RequestParam String image2Path) {
        try {
            logger.info("Received image comparison request for paths: {} and {}", image1Path, image2Path);
            ImageComparisonResponse response = calculatorGrpcClient.compareImages(image1Path, image2Path);
            logger.info("Image comparison completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during image comparison", e);
            return ResponseEntity.status(503).build();
        }
    }
} 