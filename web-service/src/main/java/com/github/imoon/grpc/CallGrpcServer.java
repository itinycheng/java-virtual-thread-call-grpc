package com.github.imoon.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Duration;

/**
 * call grpc service.
 */
@Slf4j
@GrpcService
public class CallGrpcServer extends CallGrpcServiceGrpc.CallGrpcServiceImplBase {

    @Override
    public void call(CallRequest request, StreamObserver<CallReply> responseObserver) {
        System.out.println("caller num: " + request.getNum());

        try {
            Thread.sleep(Duration.ofSeconds(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CallReply reply = CallReply.newBuilder()
                .setReply(String.format("hello %s, num:  %d", request.getName(), request.getNum()))
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
