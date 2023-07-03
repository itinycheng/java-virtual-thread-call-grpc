package com.github.imoon;

import com.github.imoon.grpc.CallGrpcServiceGrpc;
import com.github.imoon.grpc.CallRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest {


    private CallGrpcServiceGrpc.CallGrpcServiceBlockingStub stub;

    @Before
    public void before() {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress(
                                "127.0.0.1", 9898)
                        .keepAliveWithoutCalls(true)
                        .usePlaintext()
                        .build();
        stub = CallGrpcServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void ping() {
        var build = CallRequest.newBuilder().setName("moon").setNum(1).build();
        var reply = stub.call(build);
        System.out.println(reply.getReply());
    }

    @Test
    public void virtualThread() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 500_000; i++) {
                int finalI = i;
                executor.submit(() -> {
                    try {
                        var reply = stub.call(CallRequest.newBuilder().setName("moon").setNum(finalI).build());
                        System.out.printf("called: %d, %s%n", finalI, reply.getReply());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            var bool = executor.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println(bool);
        }

    }
}
