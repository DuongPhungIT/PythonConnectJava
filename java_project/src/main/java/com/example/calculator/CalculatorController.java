package com.example.calculator;

import com.example.calculator.grpc.CalculatorGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    
    private final CalculatorGrpcClient calculatorGrpcClient;

    @Autowired
    public CalculatorController(CalculatorGrpcClient calculatorGrpcClient) {
        this.calculatorGrpcClient = calculatorGrpcClient;
    }

    @PostMapping("/add")
    public ResponseEntity<Double> add(@RequestParam double a, @RequestParam double b) {
        try {
            logger.info("Received add request: {} + {}", a, b);
            double result = calculatorGrpcClient.add(a, b);
            logger.info("Add operation completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during add operation", e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 