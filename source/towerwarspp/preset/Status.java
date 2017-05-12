package towerwarspp.preset;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public class Status {
    public static final Status OK = new Status(0);
    public static final Status RED_WIN = new Status(1);
    public static final Status BLUE_WIN = new Status(2);
    public static final Status ILLEGAL = new Status(3);
    public static final Status UNDEFINED = new Status(4);

    private static String statusString[] = {"ok", "red win", "blue win", "illegal", "undefined"};

    // ------------------------------------------------------------

    private int status;

    // ------------------------------------------------------------

    private Status(int status) {
        this.status = status;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        return statusString[status];
    }
}
