package com.github.imoon.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * Job processing grpc client.
 */
@Slf4j
@Service
public class CallGrpcClient {

    private CallGrpcServiceGrpc.CallGrpcServiceBlockingStub stub;

    @PostConstruct
    public void grpcClient() {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress(
                                "127.0.0.1", 9996)
                        .keepAliveWithoutCalls(true)
                        .usePlaintext()
                        .build();
        stub = CallGrpcServiceGrpc.newBlockingStub(channel);
    }

    public CallGrpcServiceGrpc.CallGrpcServiceBlockingStub getStub() {
        return stub;
    }

}
