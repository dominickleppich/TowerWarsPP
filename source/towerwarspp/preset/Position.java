package towerwarspp.preset;

import java.io.Serializable;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public class Position implements Serializable {
    private int x, y;

    // ------------------------------------------------------------

    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    public Position(Position p) {
        setX(p.x);
        setY(p.y);
    }

    // ------------------------------------------------------------

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getX()) ^ Integer.hashCode(getY());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Position))
            return false;
        Position p = (Position) o;
        return p.getX() == getX() && p.getY() == getY();
    }
}
