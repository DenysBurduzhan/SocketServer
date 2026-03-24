package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private ExecutorService service = Executors.newFixedThreadPool(10);
    private static final int PORT = 4045;
    private List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public Server() throws IOException {

            serverSocket = new ServerSocket(PORT);
            System.out.println("start");
            clientSocket = null;

        try{
                while(true){
                    clientSocket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(clientSocket, this);
                    clients.add(client);
                    service.submit(client);
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessageToAll(String message) throws IOException {
        for(ClientHandler entry : clients){
            entry.sendMessage(message);
        }
    }

    public void removeClient(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}