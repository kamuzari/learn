package com.example.grpc.remote.client;

import com.example.grpc.AddRequest;
import com.example.grpc.AddResponse;
import com.example.grpc.CalculatorServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("grpc-client")
@Service
public class GrpcClientService {

    @GrpcClient("calculatorService")
    private CalculatorServiceGrpc.CalculatorServiceBlockingStub calculatorStub;

    public int addNumbers(int a, int b) {
        AddRequest request = AddRequest.newBuilder()
                .setA(a)
                .setB(b)
                .build();

        AddResponse response = calculatorStub.add(request);
        return response.getResult();
    }
}
