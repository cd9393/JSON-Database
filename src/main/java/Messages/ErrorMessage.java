package Messages;

public class ErrorMessage extends Response {
    private final String reason;

    public ErrorMessage(String response, String reason) {
        super(response);
        this.reason = reason;
    }
}
