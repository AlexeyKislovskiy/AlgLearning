package fertdt.exceptions;

public class MatchingEmailException extends Exception{
    public MatchingEmailException() {
    }

    public MatchingEmailException(String message) {
        super(message);
    }

    public MatchingEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchingEmailException(Throwable cause) {
        super(cause);
    }
}
