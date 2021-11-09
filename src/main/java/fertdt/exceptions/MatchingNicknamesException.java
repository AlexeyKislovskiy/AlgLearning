package fertdt.exceptions;

public class MatchingNicknamesException extends Exception {
    public MatchingNicknamesException() {
    }

    public MatchingNicknamesException(String message) {
        super(message);
    }

    public MatchingNicknamesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchingNicknamesException(Throwable cause) {
        super(cause);
    }
}
