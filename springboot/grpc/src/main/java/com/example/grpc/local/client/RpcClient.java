package com.example.grpc.local.client;

import com.example.grpc.local.factory.RpcConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RpcClient {

    public static final Socket SOCKET = new RpcConnectionFactory().getConnection();

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(SOCKET.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));

        out.println("add 3 5");
        String response = in.readLine();
        System.out.println("RPC 서버 응답: " + response);

        SOCKET.close();
    }
}
