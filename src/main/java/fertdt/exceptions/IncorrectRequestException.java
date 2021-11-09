package fertdt.exceptions;

public class IncorrectRequestException extends Exception {
    public IncorrectRequestException() {
    }

    public IncorrectRequestException(String message) {
        super(message);
    }

    public IncorrectRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectRequestException(Throwable cause) {
        super(cause);
    }
}
