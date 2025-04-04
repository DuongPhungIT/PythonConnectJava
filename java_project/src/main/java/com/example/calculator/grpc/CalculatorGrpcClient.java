package com.example.calculator.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.okhttp.OkHttpChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
public class CalculatorGrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorGrpcClient.class);
    private ManagedChannel channel;
    private CalculatorServiceGrpc.CalculatorServiceBlockingStub blockingStub;

    static {
        // Disable epoll on macOS
        System.setProperty("io.grpc.netty.shaded.io.netty.noPreferNative", "true");
        System.setProperty("io.grpc.netty.shaded.io.netty.transport.noNative", "true");
        System.setProperty("io.grpc.netty.shaded.io.netty.transport.noEpoll", "true");
        System.setProperty("io.netty.noPreferDirect", "true");
        System.setProperty("io.netty.noUnsafe", "true");
    }

    @PostConstruct
    public void init() {
        channel = OkHttpChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        blockingStub = CalculatorServiceGrpc.newBlockingStub(channel);
        logger.info("gRPC client initialized with OkHttp");
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            logger.info("gRPC client shut down");
        }
    }

    public double add(double a, double b) {
        try {
            AddRequest request = AddRequest.newBuilder()
                    .setA(a)
                    .setB(b)
                    .build();
            logger.info("Sending add request: {} + {}", a, b);
            AddResponse response = blockingStub.add(request);
            logger.info("Received add response: {}", response.getResult());
            return response.getResult();
        } catch (Exception e) {
            logger.error("Error in add operation", e);
            throw new RuntimeException("Failed to perform add operation", e);
        }
    }

    public ImageComparisonResponse compareImages(String image1Path, String image2Path) {
        try {
            ImageComparisonRequest request = ImageComparisonRequest.newBuilder()
                    .setImage1Path(image1Path)
                    .setImage2Path(image2Path)
                    .build();
            logger.info("Sending image comparison request for images: {} and {}", image1Path, image2Path);
            ImageComparisonResponse response = blockingStub.compareImages(request);
            logger.info("Received image comparison response: isSamePerson={}, image1IsFake={}, image2IsFake={}", 
                    response.getIsSamePerson(), response.getImage1IsFake(), response.getImage2IsFake());
            return response;
        } catch (Exception e) {
            logger.error("Error in image comparison operation", e);
            throw new RuntimeException("Failed to perform image comparison", e);
        }
    }
} 