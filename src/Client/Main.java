package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    private static final String SERVER_ADDRESS= "127.0.0.1";
    private static final int PORT = 34522;

    public static void main(String[] args) {
        try(
                Socket socket = new Socket(SERVER_ADDRESS,PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            output.writeUTF("Give me a record # 12");
            System.out.println("Give me a record # 12");
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
