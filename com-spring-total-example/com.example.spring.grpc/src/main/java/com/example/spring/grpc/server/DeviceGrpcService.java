package com.example.spring.grpc.server;

import com.example.spring.grpc.grpc.BooleanReply;
import com.example.spring.grpc.grpc.ConditionsRequest;
import com.example.spring.grpc.grpc.DeviceFix;
import com.example.spring.grpc.grpc.DeviceFixServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

/**
 *
 * ServerInterceptor
 */
@Slf4j
@GrpcService(DeviceFixServiceGrpc.class)
public class DeviceGrpcService extends DeviceFixServiceGrpc.DeviceFixServiceImplBase {

//    @Autowired
//    private IDevicesFixService deviceService;

    @Override
    public void insertDeviceFix(DeviceFix request, StreamObserver<BooleanReply> responseObserver) {
        responseObserver.onCompleted();
    }

    @Override
    public void updateDeviceFix(DeviceFix request, StreamObserver<BooleanReply> responseObserver) {
        super.updateDeviceFix(request, responseObserver);
    }

    @Override
    public void searchDeviceFix(ConditionsRequest request, StreamObserver<DeviceFix> responseObserver) {
        super.searchDeviceFix(request, responseObserver);
    }

    @Override
    public void deleteDeviceFix(ConditionsRequest request, StreamObserver<BooleanReply> responseObserver) {
        super.deleteDeviceFix(request, responseObserver);
    }
}