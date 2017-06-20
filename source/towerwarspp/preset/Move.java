package towerwarspp.preset;

import java.io.Serializable;

public class Move implements Serializable {
    private static final long serialVersionUID = 1L;

    private Position start;
    private Position end;

    // ------------------------------------------------------------

    public Move(Position start, Position end) {
        setStart(start);
        setEnd(end);
    }

    public Move(Move move) {
        if (move == null)
            throw new IllegalArgumentException("move == null");

        setStart(move.getStart());
        setEnd(move.getEnd());
    }

    // ------------------------------------------------------------

    public Position getStart() {
        return start;
    }

    private void setStart(Position start) {
        if (start == null)
            throw new IllegalArgumentException("start == null");

        this.start = start;
    }

    public Position getEnd() {
        return end;
    }

    private void setEnd(Position end) {
        if (end == null)
            throw new IllegalArgumentException("end == null");

        this.end = end;
    }

    // ------------------------------------------------------------

    public static Move parseMove(String str) throws IllegalArgumentException,
                                                            MoveFormatException {
        if (str == null)
            throw new IllegalArgumentException("str == null");

        // Surrender move
        if (str.isEmpty())
            return null;

        try {
            String[] params = str.split("->");
            return new Move(Position.parsePosition(params[0]),
                                   Position.parsePosition(params[1]));
        } catch (IndexOutOfBoundsException | PositionFormatException e) {
            throw new MoveFormatException("Error parsing: \"" + str + "\"", e);
        }
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
