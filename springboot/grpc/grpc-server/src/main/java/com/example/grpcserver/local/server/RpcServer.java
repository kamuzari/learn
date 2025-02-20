package com.example.grpcserver.local.server;



import com.example.grpcserver.local.factory.RpcConnectionFactory;
import com.example.grpcserver.local.server.core.CalculateService;
import com.example.grpcserver.local.server.dto.CalculateDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {
    static final ServerSocket SERVER_SOCKET = new RpcConnectionFactory().createServer();
    static final CalculateService CORE_SERVICE = new CalculateService();


    public static void main(String[] args) throws IOException {
        System.out.println("RPC Server on..");

        while (true) {
            Socket socket = SERVER_SOCKET.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request = in.readLine();
            if (request.startsWith("add")) {
                CalculateDto parameter = getParameters(request);
                int result = CORE_SERVICE.add(parameter.a(), parameter.b());
                out.println(result);
            }

            socket.close();
        }
    }

    private static CalculateDto getParameters(String request) {
        String[] parts = request.split(" ");

        try {
            return new CalculateDto(
                    Integer.parseInt(parts[1])
                    , Integer.parseInt(parts[2]));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
