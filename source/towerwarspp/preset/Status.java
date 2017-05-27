package towerwarspp.preset;

import java.io.Serializable;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public class Status implements Serializable {
    private static final int VALUE_OK = 0;
    public static final Status OK = new Status(VALUE_OK);
    private static final int VALUE_RED_WIN = 1;
    public static final Status RED_WIN = new Status(VALUE_RED_WIN);
    private static final int VALUE_BLUE_WIN = 2;
    public static final Status BLUE_WIN = new Status(VALUE_BLUE_WIN);
    private static final int VALUE_ILLEGAL = 3;
    public static final Status ILLEGAL = new Status(VALUE_ILLEGAL);
    private static final int VALUE_UNDEFINED = 4;
    public static final Status UNDEFINED = new Status(VALUE_UNDEFINED);

    private static String statusString[] = {"ok", "red win", "blue win", "illegal", "undefined"};

    // ------------------------------------------------------------

    private int status;

    // ------------------------------------------------------------

    private Status(int status) {
        this.status = status;
    }

    // ------------------------------------------------------------

    public boolean isOK() {
        return status == VALUE_OK;
    }

    public boolean isRedWin() {
        return status == VALUE_RED_WIN;
    }

    public boolean isBlueWin() {
        return status == VALUE_BLUE_WIN;
    }

    public boolean isIllegal() {
        return status == VALUE_ILLEGAL;
    }

    public boolean isUndefined() {
        return status == VALUE_UNDEFINED;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        return statusString[status];
    }
}
