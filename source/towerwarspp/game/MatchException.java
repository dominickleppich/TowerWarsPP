package towerwarspp.game;

/**
 * Created on 11.06.2017.
 *
 * @author Dominick Leppich
 */
public class MatchException extends Exception {
    public MatchException() {
        super();
    }

    public MatchException(String msg) {
        super(msg);
    }

    public MatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
