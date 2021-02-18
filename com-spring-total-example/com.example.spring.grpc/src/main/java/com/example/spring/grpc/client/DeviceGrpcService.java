package com.example.spring.grpc.client;

import com.example.spring.grpc.grpc.BooleanReply;
import com.example.spring.grpc.grpc.DeviceFix;
import com.example.spring.grpc.grpc.DeviceFixServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;


/**
 *
 * ClientInterceptor
 */
@Service
@Slf4j
public class DeviceGrpcService {

    @GrpcClient("device-grpc-server")
    private Channel serverChannel;

    public String insertDeviceFix() {
        DeviceFixServiceGrpc.DeviceFixServiceBlockingStub stub = DeviceFixServiceGrpc.newBlockingStub(serverChannel);

        BooleanReply response = stub.insertDeviceFix(
                DeviceFix.newBuilder()
                        .setId("UUID-O1")
                        .setSerialNum("AUCCMA-01")
                        .setAddress("SHENZHEN")
                        .setCreatetime(new DateFormatter().toString())
                        .setUpdatetime(new DateFormatter().toString())
                        .setStatus(1)
                        .setType(1)
                        .build());
        log.info("Grpc 消费者收到：--》" + response.getReply());
        if (response.getReply()) {
            return "success";
        } else {
            return "fail";
        }
    }
}
