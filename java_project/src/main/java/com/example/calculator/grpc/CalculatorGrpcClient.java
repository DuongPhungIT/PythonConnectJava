package com.example.calculator.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;
import com.example.calculator.grpc.CalculatorServiceGrpc;
import com.example.calculator.grpc.AddRequest;
import com.example.calculator.grpc.AddResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class CalculatorGrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorGrpcClient.class);
    private ManagedChannel channel;
    private CalculatorServiceGrpc.CalculatorServiceBlockingStub blockingStub;

    @PostConstruct
    public void init() {
        try {
            logger.info("Initializing gRPC client...");
            channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .maxRetryAttempts(3)
                    .keepAliveWithoutCalls(true)
                    .build();
            blockingStub = CalculatorServiceGrpc.newBlockingStub(channel);
            logger.info("gRPC client initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize gRPC client", e);
            throw new RuntimeException("Failed to initialize gRPC client", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdown();
            logger.info("gRPC client shutdown");
        }
    }

    public double add(double a, double b) {
        try {
            logger.info("Preparing add request: a={}, b={}", a, b);
            AddRequest request = AddRequest.newBuilder()
                    .setA(a)
                    .setB(b)
                    .build();
            
            logger.info("Sending add request to gRPC server...");
            AddResponse response = blockingStub.add(request);
            double result = response.getResult();
            logger.info("Received add response: result={}", result);
            return result;
        } catch (StatusRuntimeException e) {
            logger.error("gRPC error during add operation: {}", e.getStatus().getDescription());
            throw new RuntimeException("gRPC error: " + e.getStatus().getDescription(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during add operation", e);
            throw new RuntimeException("Failed to perform add operation: " + e.getMessage(), e);
        }
    }
} 