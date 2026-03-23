package org.example.Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Server server;
    private Scanner inMessage;
    private PrintWriter printWriter;
    private static int client_count = 0;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server){
        try{
            client_count++;
            this.server = server;
            this.clientSocket = socket;
            this.printWriter = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message){
        printWriter.println(message);
        printWriter.flush();
    }
    @Override
    public void run() {
        try{
            while (true){

                break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
