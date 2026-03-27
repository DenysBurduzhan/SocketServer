package Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private String name;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket clientSocket ;

    public ClientHandler(Socket socket){
        try{
            this.clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() throws IOException {
        ClientManager.removeClient(this);
        ClientManager.sendMessageToAll(name + " left the chat." + " Members: " + ClientManager.getClients().size());
        clientSocket.close();
    }

    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (true){
            try {
                out.write("Enter yor name:");
                out.newLine();
                out.flush();

                String inputName = in.readLine();
                if (inputName == null) {
                    closeConnection();
                    return;
                }

                if(!ClientManager.isNameTaken(inputName)){
                    this.name = inputName;
                    ClientManager.sendMessageToAll(name + " joined the chat");
                    break;
                }else{
                    out.write("Name already taken, try again");
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try{
                String message;
            while ((message = in.readLine()) != null){
                if (message.startsWith("/pm")) {

                    String[] parts = message.split(" ", 3);

                    if (parts.length < 3) {
                        sendMessage("Usage: /pm <name> <message>");
                        continue;
                    }

                    String targetName = parts[1];
                    String privateMessage = parts[2];

                    ClientManager.sendPrivateMessage(name, targetName, privateMessage);
                    continue;

                }
                if(message.equalsIgnoreCase("END")){
                    break;
                }
                System.out.println(message);
                ClientManager.sendMessageToAll(name +": " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println(name + " disconnected");
                closeConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
