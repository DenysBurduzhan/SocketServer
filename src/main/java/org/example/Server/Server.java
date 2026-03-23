package org.example.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("start");
        Socket socket = serverSocket.accept();
        System.out.println("+");

        BufferedWriter writer =
                new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()));
        writer.write("Hello from server");
        writer.newLine();
        writer.flush();

        writer.close();
        socket.close();
        serverSocket.close();
    }
}