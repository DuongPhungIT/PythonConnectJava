package com.example.calculator.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The calculator service definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.42.2)",
    comments = "Source: calculator.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CalculatorServiceGrpc {

  private CalculatorServiceGrpc() {}

  public static final String SERVICE_NAME = "com.example.calculator.grpc.CalculatorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.calculator.grpc.AddRequest,
      com.example.calculator.grpc.AddResponse> getAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "add",
      requestType = com.example.calculator.grpc.AddRequest.class,
      responseType = com.example.calculator.grpc.AddResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.calculator.grpc.AddRequest,
      com.example.calculator.grpc.AddResponse> getAddMethod() {
    io.grpc.MethodDescriptor<com.example.calculator.grpc.AddRequest, com.example.calculator.grpc.AddResponse> getAddMethod;
    if ((getAddMethod = CalculatorServiceGrpc.getAddMethod) == null) {
      synchronized (CalculatorServiceGrpc.class) {
        if ((getAddMethod = CalculatorServiceGrpc.getAddMethod) == null) {
          CalculatorServiceGrpc.getAddMethod = getAddMethod =
              io.grpc.MethodDescriptor.<com.example.calculator.grpc.AddRequest, com.example.calculator.grpc.AddResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "add"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.calculator.grpc.AddRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.calculator.grpc.AddResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CalculatorServiceMethodDescriptorSupplier("add"))
              .build();
        }
      }
    }
    return getAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.calculator.grpc.ImageComparisonRequest,
      com.example.calculator.grpc.ImageComparisonResponse> getCompareImagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "compareImages",
      requestType = com.example.calculator.grpc.ImageComparisonRequest.class,
      responseType = com.example.calculator.grpc.ImageComparisonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.calculator.grpc.ImageComparisonRequest,
      com.example.calculator.grpc.ImageComparisonResponse> getCompareImagesMethod() {
    io.grpc.MethodDescriptor<com.example.calculator.grpc.ImageComparisonRequest, com.example.calculator.grpc.ImageComparisonResponse> getCompareImagesMethod;
    if ((getCompareImagesMethod = CalculatorServiceGrpc.getCompareImagesMethod) == null) {
      synchronized (CalculatorServiceGrpc.class) {
        if ((getCompareImagesMethod = CalculatorServiceGrpc.getCompareImagesMethod) == null) {
          CalculatorServiceGrpc.getCompareImagesMethod = getCompareImagesMethod =
              io.grpc.MethodDescriptor.<com.example.calculator.grpc.ImageComparisonRequest, com.example.calculator.grpc.ImageComparisonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "compareImages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.calculator.grpc.ImageComparisonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.calculator.grpc.ImageComparisonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CalculatorServiceMethodDescriptorSupplier("compareImages"))
              .build();
        }
      }
    }
    return getCompareImagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CalculatorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalculatorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalculatorServiceStub>() {
        @java.lang.Override
        public CalculatorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalculatorServiceStub(channel, callOptions);
        }
      };
    return CalculatorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CalculatorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalculatorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalculatorServiceBlockingStub>() {
        @java.lang.Override
        public CalculatorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalculatorServiceBlockingStub(channel, callOptions);
        }
      };
    return CalculatorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CalculatorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalculatorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalculatorServiceFutureStub>() {
        @java.lang.Override
        public CalculatorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalculatorServiceFutureStub(channel, callOptions);
        }
      };
    return CalculatorServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The calculator service definition
   * </pre>
   */
  public static abstract class CalculatorServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Add two numbers
     * </pre>
     */
    public void add(com.example.calculator.grpc.AddRequest request,
        io.grpc.stub.StreamObserver<com.example.calculator.grpc.AddResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddMethod(), responseObserver);
    }

    /**
     * <pre>
     * Compare two images and check if they are the same person
     * </pre>
     */
    public void compareImages(com.example.calculator.grpc.ImageComparisonRequest request,
        io.grpc.stub.StreamObserver<com.example.calculator.grpc.ImageComparisonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCompareImagesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.calculator.grpc.AddRequest,
                com.example.calculator.grpc.AddResponse>(
                  this, METHODID_ADD)))
          .addMethod(
            getCompareImagesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.calculator.grpc.ImageComparisonRequest,
                com.example.calculator.grpc.ImageComparisonResponse>(
                  this, METHODID_COMPARE_IMAGES)))
          .build();
    }
  }

  /**
   * <pre>
   * The calculator service definition
   * </pre>
   */
  public static final class CalculatorServiceStub extends io.grpc.stub.AbstractAsyncStub<CalculatorServiceStub> {
    private CalculatorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculatorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalculatorServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Add two numbers
     * </pre>
     */
    public void add(com.example.calculator.grpc.AddRequest request,
        io.grpc.stub.StreamObserver<com.example.calculator.grpc.AddResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Compare two images and check if they are the same person
     * </pre>
     */
    public void compareImages(com.example.calculator.grpc.ImageComparisonRequest request,
        io.grpc.stub.StreamObserver<com.example.calculator.grpc.ImageComparisonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCompareImagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The calculator service definition
   * </pre>
   */
  public static final class CalculatorServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CalculatorServiceBlockingStub> {
    private CalculatorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculatorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalculatorServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Add two numbers
     * </pre>
     */
    public com.example.calculator.grpc.AddResponse add(com.example.calculator.grpc.AddRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Compare two images and check if they are the same person
     * </pre>
     */
    public com.example.calculator.grpc.ImageComparisonResponse compareImages(com.example.calculator.grpc.ImageComparisonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCompareImagesMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The calculator service definition
   * </pre>
   */
  public static final class CalculatorServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CalculatorServiceFutureStub> {
    private CalculatorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculatorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalculatorServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Add two numbers
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.calculator.grpc.AddResponse> add(
        com.example.calculator.grpc.AddRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Compare two images and check if they are the same person
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.calculator.grpc.ImageComparisonResponse> compareImages(
        com.example.calculator.grpc.ImageComparisonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCompareImagesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD = 0;
  private static final int METHODID_COMPARE_IMAGES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CalculatorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CalculatorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD:
          serviceImpl.add((com.example.calculator.grpc.AddRequest) request,
              (io.grpc.stub.StreamObserver<com.example.calculator.grpc.AddResponse>) responseObserver);
          break;
        case METHODID_COMPARE_IMAGES:
          serviceImpl.compareImages((com.example.calculator.grpc.ImageComparisonRequest) request,
              (io.grpc.stub.StreamObserver<com.example.calculator.grpc.ImageComparisonResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CalculatorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CalculatorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.calculator.grpc.CalculatorProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CalculatorService");
    }
  }

  private static final class CalculatorServiceFileDescriptorSupplier
      extends CalculatorServiceBaseDescriptorSupplier {
    CalculatorServiceFileDescriptorSupplier() {}
  }

  private static final class CalculatorServiceMethodDescriptorSupplier
      extends CalculatorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CalculatorServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CalculatorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CalculatorServiceFileDescriptorSupplier())
              .addMethod(getAddMethod())
              .addMethod(getCompareImagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
