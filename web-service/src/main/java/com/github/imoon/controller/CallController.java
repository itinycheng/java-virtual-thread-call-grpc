package com.github.imoon.controller;

import com.github.imoon.grpc.CallGrpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call")
public class CallController {
    
    @Autowired
    private CallGrpcServer callService;

    @RequestMapping("/test")
    public String call() {
        System.out.println("callService");
        return "called";
    }
}
