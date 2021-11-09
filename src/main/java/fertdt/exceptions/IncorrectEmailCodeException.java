package fertdt.exceptions;

public class IncorrectEmailCodeException extends Exception{
    public IncorrectEmailCodeException() {
    }

    public IncorrectEmailCodeException(String message) {
        super(message);
    }

    public IncorrectEmailCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectEmailCodeException(Throwable cause) {
        super(cause);
    }
}
