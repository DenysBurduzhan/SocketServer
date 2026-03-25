package Server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClientManager {
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();


    public static void addClient(ClientHandler client){
        clients.add(client);
    }

    public static List<ClientHandler> getClients() {
        return clients;
    }


    public static void sendMessageToAll(String message) throws IOException {
        for(ClientHandler entry : clients){
            entry.sendMessage(message);
        }
    }

    public static void removeClient(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }
}
