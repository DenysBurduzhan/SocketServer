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

    public static void checkClientName(ClientHandler client) throws IOException {
        if(clients.isEmpty()){
            ClientManager.sendMessageToAll("NEW CLIENT CONNECTED");
            ClientManager.sendMessageToAll(ClientHandler.getCount() + "");
        }else if(clients.contains(client)){
            System.out.println("client already exists");
        }else{
            addClient(client);
            ClientManager.sendMessageToAll("NEW CLIENT CONNECTED");
            ClientManager.sendMessageToAll(ClientHandler.getCount() + "");
        }
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
