package Messages;

public class GetResponse extends Response{
    private final String value;

    public GetResponse(String response, String value) {
        super(response);
        this.value = value;
    }
}
