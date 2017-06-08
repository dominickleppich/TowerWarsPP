package towerwarspp.board;

/**
 * Created on 08.06.2017.
 *
 * @author dominick
 */
public class IllegalMoveException extends IllegalArgumentException {
    public IllegalMoveException() {

    }

    public IllegalMoveException(String msg) {
        super(msg);
    }
}
