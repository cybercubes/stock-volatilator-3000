package ee.taltech.volatilator.responses;

public abstract class Response<T> {
    public String status;
    public T body;

    public Response(T body) {
        this.body = body;
    }
}
