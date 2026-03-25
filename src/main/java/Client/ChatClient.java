package Client;

import Server.Server;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        try {
            clientSocket = new Socket("localhost", Server.getPORT());

            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true){
            String message = reader.readLine();
            out.println(message);
        }
    }
}
