package towerwarspp.preset;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public interface Setting {
    // color ======================================================
    // game -------------------------------------------------------
    int RED = 0;
    int BLUE = 1;

    // extra ------------------------------------------------------
    int NONE = 2; // empty square
    int WHITE = 3; // for special duty
    int GRAY = 4; // for special duty

    String[] colorString = {"RED", "BLUE", "NONE", "WHITE", "GRAY"};

    // status =====================================================
    int OK = 0;
    int RED_WIN = 1;
    int BLUE_WIN = 2;
    int ILLEGAL = 3;
    int UNDEFINED = 4;

    public static final String[] statusString = {"ok", "red win", "blue win", "illegal", "undefined"};
}
