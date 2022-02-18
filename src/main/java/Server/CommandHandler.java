package Server;
import Messages.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class CommandHandler {
    private final JSONDatabase database;
    private final Request request;

    public CommandHandler(Request request, JSONDatabase database) {
        this.request = request;
        this.database = database;
    }

    public String run() {
        String result;
        Gson gson = new Gson();
        switch (request.getType()) {
            case "get":
                JsonElement getResult = database.get(request.getKey());
                if (getResult == null) {
                    return gson.toJson(new ErrorMessage("ERROR","No such key"));
                }
                return gson.toJson(new GetResponse("OK",getResult));
            case "set":
                database.set(request.getKey(),request.getValue());
                return gson.toJson(new Response("OK"));
            case "delete":
                result = database.delete(request.getKey());
                if("ERROR".equals(result)) {
                    return gson.toJson(new ErrorMessage("ERROR","No such key"));
                }
                return gson.toJson(new Response("OK"));
            default:
                return gson.toJson(new ErrorMessage("ERROR","No such key"));
        }
    }
}
