package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
private static final int PORT = 34522;

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        try(ServerSocket server = new ServerSocket(PORT,50, InetAddress.getByName("127.0.0.1"))) {
                try(
                        Socket socket = server.accept(); // accepting a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String receivedMessage = input.readUTF();
                    System.out.println("Received: " + receivedMessage);
                    output.writeUTF("A record # " + parseNumber(receivedMessage) + " was sent!");
                    System.out.println("Sent: A record # " + parseNumber(receivedMessage) + " was sent!");

                }
        }
    }

    private static int parseNumber(String msg) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(msg);
        int result = 0;
        while(matcher.find()) {
             result = Integer.parseInt(matcher.group());
             break;
        }
        return result;
    }
}
