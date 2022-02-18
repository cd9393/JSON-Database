package Messages;

import com.google.gson.JsonElement;

public class Request {
    private final String type;
    private final JsonElement key;
    private final JsonElement value;

    public Request(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public Request(String type, JsonElement key) {
        this.type = type;
        this.key = key;
        this.value = null;
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Request{")
                .append("type: ")
                .append(type)
                .append(", key: ")
                .append(key)
                .append(", value: ")
                .append(value)
                .append("}")
                .toString();
    }
}