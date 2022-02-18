package Client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import Messages.Request;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String SERVER_ADDRESS= "127.0.0.1";
    private static final int PORT = 23446;
    @Parameter(names = "-t")
    String command;

    @Parameter(names = "-k")
    String key;

    @Parameter(names = "-v")
    String value;

    @Parameter(names = "-in")
    String fileName;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        String request;
        if (main.fileName == null) {
            request = main.createJSON();
        } else {
            request = main.readJSONFromFile();
        }
        main.runClient(request);

    }

    private void runClient(String request) {
        try(
                Socket socket = new Socket(SERVER_ADDRESS,PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            output.writeUTF(request);
            System.out.println("Sent: " + request);
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return command + "," + key + "," + value;
    }

    private String createJSON() {
        Map<String, String> request = new HashMap<>();
        request.put("type",command);
        request.put("key",key);
        request.put("value",value);
        return new Gson().toJson(request);
    }

    private String readJSONFromFile() {
        String filePath = "src/client/data/".concat(fileName);
        Path path = new File(filePath).toPath();
        Gson gson = new Gson();
        String requestJSON = "";
        try(Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Request request = gson.fromJson(reader, Request.class);
            requestJSON = gson.toJson(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestJSON;
    }
}