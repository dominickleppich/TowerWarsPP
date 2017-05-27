package towerwarspp.preset;

import java.io.Serializable;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public class Move implements Serializable {
    private Position start;
    private Position end;

    // ------------------------------------------------------------

    public Move(Position start, Position end) {
        setStart(start);
        setEnd(end);
    }

    // ------------------------------------------------------------

    public Position getStart() {
        return start;
    }

    private void setStart(Position start) {
        this.start = start;
    }

    public Position getEnd() {
        return end;
    }

    private void setEnd(Position end) {
        this.end = end;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        return getStart() + "->" + getEnd();
    }

    @Override
    public int hashCode() {
        return getStart().hashCode() ^ getEnd().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Move))
            return false;
        Move m = (Move) o;
        return m.getStart().equals(getStart()) && m.getEnd().equals(getEnd());
    }
}
