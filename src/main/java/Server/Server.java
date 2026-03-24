package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private ExecutorService service = Executors.newFixedThreadPool(10);
    private static final int PORT = 4045;

    public Server() throws IOException {

            serverSocket = new ServerSocket(PORT);
            System.out.println("start");
            clientSocket = null;

        try{
                while(true){
                    clientSocket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(clientSocket, this);
                    ClientManager.checkClientName(client);
                    service.submit(client);
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPORT() {
        return PORT;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}