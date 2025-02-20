package com.example.grpcserver;

import com.example.grpc.AddRequest;
import com.example.grpc.AddResponse;
import com.example.grpc.CalculatorServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void add(AddRequest request, StreamObserver<AddResponse> responseObserver) {
        int result = request.getA() + request.getB();

        AddResponse response = AddResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
