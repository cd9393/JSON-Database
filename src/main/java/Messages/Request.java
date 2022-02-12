package Messages;

public class Request {
    private final String type;
    private final String key;
    private final String value;

    public Request(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public Request(String type, String key) {
        this.type = type;
        this.key = key;
        this.value = null;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
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
