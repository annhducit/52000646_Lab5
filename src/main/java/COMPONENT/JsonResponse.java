package COMPONENT;

public class JsonResponse {
    private int error;
    private String message;
    private Object data;

    public JsonResponse() {
    }

    public JsonResponse(int error, String message, Object data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
