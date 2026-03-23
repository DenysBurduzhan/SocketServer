package org.example.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 4045;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() throws IOException {

            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("start");
            Socket clientSocket = null;

        try{
            while (true){
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket,this);
                clients.add(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            clientSocket.close();
            serverSocket.close();
        }
    }
    public void sendMessageToAll(String message){
        for(ClientHandler entry : clients){
            entry.sendMessage(message);
        }

    }

    public void removeClient(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public static void main(String[] args) throws IOException {

    }
}