package ee.taltech.volatilator.responses;

public class ErrorResponse<T> extends Response<T> {
    public String status = "error";
    public String message;
    public String code;

    public ErrorResponse(String message) {
        super(null);
        this.message = message;
    }

    public ErrorResponse(String message, T body, String code) {
        super(body);
        this.message = message;
        this.code = code;
    }
}
