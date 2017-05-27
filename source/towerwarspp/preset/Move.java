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

    /**
     * Exception for wrong {@link Move} parse format
     */
    public static class MoveFormatException extends IllegalArgumentException {
        public MoveFormatException(String msg) {
            super(msg);
        }
    }

    /**
     * Parse a string to a {@link Move}
     *
     * @param str
     *         String to parse
     *
     * @return Parsed {@link Move}
     *
     * @throws MoveFormatException
     *         if parsing fails
     */
    public static Move parseMove(String str) throws MoveFormatException {
        // Surrender move
        if (str == null || str.equals(""))
            return null;

        Position start, end;
        try {
            String[] params = str.split("->");
            start = Position.parsePosition(params[0]);
            end = Position.parsePosition(params[1]);
        } catch (NullPointerException | IndexOutOfBoundsException | Position.PositionFormatException e) {
            throw new MoveFormatException("Unable to parse move: " + str);
        }
        return new Move(start, end);
    }

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
