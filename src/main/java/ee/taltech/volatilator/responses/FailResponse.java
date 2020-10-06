package ee.taltech.volatilator.responses;

public class FailResponse<T> extends Response<T> {
    public String status = "fail";

    public FailResponse(T body) {
        super(body);
    }
}
