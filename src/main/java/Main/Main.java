package Main;

import Client.ChatClient;

public class Main {
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.start();
    }
}
