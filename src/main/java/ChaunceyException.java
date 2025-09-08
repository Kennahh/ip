public class ChaunceyException extends Exception {
    public ChaunceyException() {
        super();
    }

    public ChaunceyException(String message) {
        super(message);
    }

    public ChaunceyException(String message, Throwable cause) {
        super(message, cause);
    }
}
