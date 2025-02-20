package com.example.grpcclient;


import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcTestController {

    private final GrpcClientService grpcClientService;

    public GrpcTestController(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    @GetMapping("/add")
    public String add(@RequestParam int a, @RequestParam int b) {
        int result = grpcClientService.addNumbers(a, b);
        return "결과: " + result;
    }
}
