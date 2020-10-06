package ee.taltech.volatilator.responses;

public class SuccessResponse<T> extends Response<T> {
    public String status = "success";

    public SuccessResponse(T body) {
        super(body);
    }
}
