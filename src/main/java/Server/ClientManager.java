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

    public static ClientHandler getByName(String name){
        for(ClientHandler client : clients){
            if(client.getName().equals(name)){
                return client;
            }
        }
        return null;
    }
    public static void sendPrivateMessage(String from, String to, String message) throws IOException {
        ClientHandler target = getByName(to);
        if(target != null){
            target.sendMessage("[PM] " + ": " + message);
        }
        ClientHandler sender = getByName(from);
        if (sender != null) {
            sender.sendMessage("[PM to " + to + "]: " + message);
        }
    }

    public static boolean isNameTaken(String name) {
        for(ClientHandler client : clients){
            if(client.getName() != null && client.getName().equals(name)){
                return true;
            }
        }
        return false;
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
