package Messages;


import com.google.gson.JsonElement;

public class GetResponse extends Response{
    private final JsonElement value;

    public GetResponse(String response, JsonElement value) {
        super(response);
        this.value = value;
    }

    public GetResponse(String response) {
        super(response);
        this.value = null;
    }
}
