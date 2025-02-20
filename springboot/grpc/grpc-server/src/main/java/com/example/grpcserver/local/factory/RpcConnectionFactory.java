package com.example.grpcserver.local.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcConnectionFactory {
    public static final int SERVER_PORT = 10001;

    public Socket getConnection() {
        try {
            return new Socket("localhost", SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException("connect refuse... ", e);
        }
    }

    public ServerSocket createServer() {
        try {
            return new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException("not create ... ", e);
        }
    }
}
