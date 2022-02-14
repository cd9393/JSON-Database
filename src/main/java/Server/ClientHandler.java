package Server;

import Messages.Request;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Messages.Response;

public class ClientHandler implements Runnable {

    private final Socket client;
    private ServerSocket server;
    private final JSONDatabase database;

    public ClientHandler(Socket client, ServerSocket server,JSONDatabase database) {
        this.client = client;
        this.server = server;
        this.database = database;
    }

    @Override
    public void run() {

        try(
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream())
        ) {
            String receivedMessage = input.readUTF();
            Gson gson = new Gson();
            Request request = gson.fromJson(receivedMessage,Request.class);
            if ("exit".equals(request.getType())) {
                Response response = new Response("OK");
                output.writeUTF(gson.toJson(response));
                server.close();
                return;
            } else {
                CommandHandler handler = new CommandHandler(request,database);
                String response = handler.run();
                System.out.println(response);
                output.writeUTF(response);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}