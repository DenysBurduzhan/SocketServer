package Server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler implements Runnable{
    private Server server;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server){
        try{
            this.server = server;
            this.clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() throws IOException {
        ClientManager.removeClient(this);
        ClientManager.sendMessageToAll(String.valueOf(ClientManager.getClients().size()));
    }

    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    @Override
    public void run() {
        try{
                String message;
            while ((message = in.readLine()) != null){
                if(message == null){
                    System.out.println("client disconnected");
                    break;
                }
                if(message.equalsIgnoreCase("END")){
                    break;
                }
                System.out.println(message);
                ClientManager.sendMessageToAll(message);
            }
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                closeConnection();
                clientSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
