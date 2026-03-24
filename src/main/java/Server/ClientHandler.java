package Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Server server;
    private BufferedReader in;
    private BufferedWriter out;
    private static int client_count = 0;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server){
        try{
            client_count++;
            this.server = server;
            this.clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws IOException {
        server.removeClient(this);
        client_count--;
        server.sendMessageToAll(String.valueOf(client_count));
    }

    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }
    public String readMessage() throws IOException {
        return in.readLine();
    }
    @Override
    public void run() {
        try{
                server.sendMessageToAll("NEW CLIENT CONNECTED");
                server.sendMessageToAll(client_count + "");
                String message;
            while ((message = in.readLine()) != null){
                if(message.equalsIgnoreCase("END")){
                    break;
                }
                System.out.println(message);
                server.sendMessageToAll(message);
            }
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                closeConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
