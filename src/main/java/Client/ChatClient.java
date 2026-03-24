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
        try{
            clientSocket = new Socket("localhost", Server.getPORT());

            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                String word = reader.readLine();
                out.println(word);
                String serverWord = in.readLine();
                System.out.println(serverWord);
            }

        } catch (IOException e) {
            System.err.println(e);
        } finally {
            System.out.println("close");
            clientSocket.close();
            in.close();
            out.close();
        }
    }
}
