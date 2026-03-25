package Client;

import Server.Server;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedReader in;
    private PrintWriter out;

    public void start() throws IOException {

            clientSocket = new Socket("localhost", Server.getPORT());

            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(new MessageReader(in)).start();
            while (true) {
                String message = reader.readLine();
                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
                out.println(message);
            }
            close();
    }
    private void close() throws IOException {
        clientSocket.close();
        in.close();
        out.close();
        reader.close();
    }
}
