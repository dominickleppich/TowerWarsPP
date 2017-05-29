package towerwarspp.preset;

public class MoveFormatException extends IllegalArgumentException {
    public MoveFormatException(String msg) {
        super(msg);
    }

    public MoveFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}