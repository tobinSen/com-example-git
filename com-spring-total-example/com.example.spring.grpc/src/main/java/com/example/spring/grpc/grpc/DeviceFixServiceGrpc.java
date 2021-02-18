package com.example.spring.grpc.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * 定义接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: device.proto")
public final class DeviceFixServiceGrpc {

  private DeviceFixServiceGrpc() {}

  public static final String SERVICE_NAME = "com.example.spring.grpc.DeviceFixService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.example.spring.grpc.grpc.DeviceFix,
      com.example.spring.grpc.grpc.BooleanReply> METHOD_INSERT_DEVICE_FIX =
      io.grpc.MethodDescriptor.<com.example.spring.grpc.grpc.DeviceFix, com.example.spring.grpc.grpc.BooleanReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.example.spring.grpc.DeviceFixService", "insertDeviceFix"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.DeviceFix.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.BooleanReply.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.example.spring.grpc.grpc.DeviceFix,
      com.example.spring.grpc.grpc.BooleanReply> METHOD_UPDATE_DEVICE_FIX =
      io.grpc.MethodDescriptor.<com.example.spring.grpc.grpc.DeviceFix, com.example.spring.grpc.grpc.BooleanReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.example.spring.grpc.DeviceFixService", "updateDeviceFix"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.DeviceFix.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.BooleanReply.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.example.spring.grpc.grpc.ConditionsRequest,
      com.example.spring.grpc.grpc.DeviceFix> METHOD_SEARCH_DEVICE_FIX =
      io.grpc.MethodDescriptor.<com.example.spring.grpc.grpc.ConditionsRequest, com.example.spring.grpc.grpc.DeviceFix>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.example.spring.grpc.DeviceFixService", "searchDeviceFix"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.ConditionsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.DeviceFix.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.example.spring.grpc.grpc.ConditionsRequest,
      com.example.spring.grpc.grpc.BooleanReply> METHOD_DELETE_DEVICE_FIX =
      io.grpc.MethodDescriptor.<com.example.spring.grpc.grpc.ConditionsRequest, com.example.spring.grpc.grpc.BooleanReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.example.spring.grpc.DeviceFixService", "deleteDeviceFix"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.ConditionsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.example.spring.grpc.grpc.BooleanReply.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DeviceFixServiceStub newStub(io.grpc.Channel channel) {
    return new DeviceFixServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DeviceFixServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DeviceFixServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DeviceFixServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DeviceFixServiceFutureStub(channel);
  }

  /**
   * <pre>
   * 定义接口
   * </pre>
   */
  public static abstract class DeviceFixServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public void insertDeviceFix(com.example.spring.grpc.grpc.DeviceFix request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_DEVICE_FIX, responseObserver);
    }

    /**
     */
    public void updateDeviceFix(com.example.spring.grpc.grpc.DeviceFix request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_DEVICE_FIX, responseObserver);
    }

    /**
     */
    public void searchDeviceFix(com.example.spring.grpc.grpc.ConditionsRequest request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.DeviceFix> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_DEVICE_FIX, responseObserver);
    }

    /**
     */
    public void deleteDeviceFix(com.example.spring.grpc.grpc.ConditionsRequest request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETE_DEVICE_FIX, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_DEVICE_FIX,
            asyncUnaryCall(
              new MethodHandlers<
                com.example.spring.grpc.grpc.DeviceFix,
                com.example.spring.grpc.grpc.BooleanReply>(
                  this, METHODID_INSERT_DEVICE_FIX)))
          .addMethod(
            METHOD_UPDATE_DEVICE_FIX,
            asyncUnaryCall(
              new MethodHandlers<
                com.example.spring.grpc.grpc.DeviceFix,
                com.example.spring.grpc.grpc.BooleanReply>(
                  this, METHODID_UPDATE_DEVICE_FIX)))
          .addMethod(
            METHOD_SEARCH_DEVICE_FIX,
            asyncUnaryCall(
              new MethodHandlers<
                com.example.spring.grpc.grpc.ConditionsRequest,
                com.example.spring.grpc.grpc.DeviceFix>(
                  this, METHODID_SEARCH_DEVICE_FIX)))
          .addMethod(
            METHOD_DELETE_DEVICE_FIX,
            asyncUnaryCall(
              new MethodHandlers<
                com.example.spring.grpc.grpc.ConditionsRequest,
                com.example.spring.grpc.grpc.BooleanReply>(
                  this, METHODID_DELETE_DEVICE_FIX)))
          .build();
    }
  }

  /**
   * <pre>
   * 定义接口
   * </pre>
   */
  public static final class DeviceFixServiceStub extends io.grpc.stub.AbstractStub<DeviceFixServiceStub> {
    private DeviceFixServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeviceFixServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceFixServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeviceFixServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public void insertDeviceFix(com.example.spring.grpc.grpc.DeviceFix request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_DEVICE_FIX, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateDeviceFix(com.example.spring.grpc.grpc.DeviceFix request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DEVICE_FIX, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchDeviceFix(com.example.spring.grpc.grpc.ConditionsRequest request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.DeviceFix> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_DEVICE_FIX, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteDeviceFix(com.example.spring.grpc.grpc.ConditionsRequest request,
        io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_DEVICE_FIX, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * 定义接口
   * </pre>
   */
  public static final class DeviceFixServiceBlockingStub extends io.grpc.stub.AbstractStub<DeviceFixServiceBlockingStub> {
    private DeviceFixServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeviceFixServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceFixServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeviceFixServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public com.example.spring.grpc.grpc.BooleanReply insertDeviceFix(com.example.spring.grpc.grpc.DeviceFix request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_DEVICE_FIX, getCallOptions(), request);
    }

    /**
     */
    public com.example.spring.grpc.grpc.BooleanReply updateDeviceFix(com.example.spring.grpc.grpc.DeviceFix request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_DEVICE_FIX, getCallOptions(), request);
    }

    /**
     */
    public com.example.spring.grpc.grpc.DeviceFix searchDeviceFix(com.example.spring.grpc.grpc.ConditionsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_DEVICE_FIX, getCallOptions(), request);
    }

    /**
     */
    public com.example.spring.grpc.grpc.BooleanReply deleteDeviceFix(com.example.spring.grpc.grpc.ConditionsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_DEVICE_FIX, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * 定义接口
   * </pre>
   */
  public static final class DeviceFixServiceFutureStub extends io.grpc.stub.AbstractStub<DeviceFixServiceFutureStub> {
    private DeviceFixServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeviceFixServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceFixServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeviceFixServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.spring.grpc.grpc.BooleanReply> insertDeviceFix(
        com.example.spring.grpc.grpc.DeviceFix request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_DEVICE_FIX, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.spring.grpc.grpc.BooleanReply> updateDeviceFix(
        com.example.spring.grpc.grpc.DeviceFix request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DEVICE_FIX, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.spring.grpc.grpc.DeviceFix> searchDeviceFix(
        com.example.spring.grpc.grpc.ConditionsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_DEVICE_FIX, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.spring.grpc.grpc.BooleanReply> deleteDeviceFix(
        com.example.spring.grpc.grpc.ConditionsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_DEVICE_FIX, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_DEVICE_FIX = 0;
  private static final int METHODID_UPDATE_DEVICE_FIX = 1;
  private static final int METHODID_SEARCH_DEVICE_FIX = 2;
  private static final int METHODID_DELETE_DEVICE_FIX = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DeviceFixServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DeviceFixServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_DEVICE_FIX:
          serviceImpl.insertDeviceFix((com.example.spring.grpc.grpc.DeviceFix) request,
              (io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply>) responseObserver);
          break;
        case METHODID_UPDATE_DEVICE_FIX:
          serviceImpl.updateDeviceFix((com.example.spring.grpc.grpc.DeviceFix) request,
              (io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply>) responseObserver);
          break;
        case METHODID_SEARCH_DEVICE_FIX:
          serviceImpl.searchDeviceFix((com.example.spring.grpc.grpc.ConditionsRequest) request,
              (io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.DeviceFix>) responseObserver);
          break;
        case METHODID_DELETE_DEVICE_FIX:
          serviceImpl.deleteDeviceFix((com.example.spring.grpc.grpc.ConditionsRequest) request,
              (io.grpc.stub.StreamObserver<com.example.spring.grpc.grpc.BooleanReply>) responseObserver);
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

  private static final class DeviceFixServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.spring.grpc.grpc.DeviceFixProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DeviceFixServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DeviceFixServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_DEVICE_FIX)
              .addMethod(METHOD_UPDATE_DEVICE_FIX)
              .addMethod(METHOD_SEARCH_DEVICE_FIX)
              .addMethod(METHOD_DELETE_DEVICE_FIX)
              .build();
        }
      }
    }
    return result;
  }
}
