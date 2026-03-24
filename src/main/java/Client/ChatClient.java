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
            while (true) {
                clientSocket = new Socket("localhost", Server.getPORT());
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String word = reader.readLine();
                System.out.println(word);
                out.write(word);
                out.flush();
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
