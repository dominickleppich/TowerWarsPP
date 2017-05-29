package towerwarspp.preset;

public class PositionFormatException extends IllegalArgumentException {
    public PositionFormatException(String msg) {
        super(msg);
    }

    public PositionFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

