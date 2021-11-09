package fertdt.exceptions;

public class MatchingPasswordsException extends Exception {
    public MatchingPasswordsException() {
    }

    public MatchingPasswordsException(String message) {
        super(message);
    }

    public MatchingPasswordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchingPasswordsException(Throwable cause) {
        super(cause);
    }
}
