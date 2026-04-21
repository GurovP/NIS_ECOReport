package eco.nis.api;

public class ResponseWrapper<T> {
    private T result;
    private String error;

    public ResponseWrapper(T result) {
        this.result = result;
    }

    public ResponseWrapper(String error) {
        this.error = error;
    }

    public ResponseWrapper(T result, String error) {
        this.result = result;
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
