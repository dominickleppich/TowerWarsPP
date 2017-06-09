package towerwarspp.preset;

public class ArgumentParserException extends Exception {
    public ArgumentParserException() {

    }

    public ArgumentParserException(String msg) {
        super(msg);
    }

    public ArgumentParserException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
