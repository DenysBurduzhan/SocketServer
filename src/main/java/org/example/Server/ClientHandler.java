package org.example.Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Server server;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private static int client_count = 0;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server){
        try{
            client_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        server.removeClient(this);
        client_count--;
        server.sendMessageToAll(String.valueOf(client_count));
    }

    public void sendMessage(String message){
        outMessage.println(message);
        outMessage.flush();
    }
    @Override
    public void run() {
        try{
            while (true){
                server.sendMessageToAll("NEW CLIENT");
                server.sendMessageToAll(client_count + "");
                break;
            }
            while (true){
                String clientMessage = inMessage.nextLine();
                if(clientMessage.equals("END")){
                    break;
                }
                System.out.println(clientMessage);
                server.sendMessageToAll(clientMessage);
            }
            Thread.sleep(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
