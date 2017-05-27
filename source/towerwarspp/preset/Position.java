package towerwarspp.preset;

import java.io.Serializable;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public class Position implements Serializable {
    private int letter, number;

    // ------------------------------------------------------------

    public Position(int letter, int number) {
        setLetter(letter);
        setNumber(number);
    }

    public Position(Position p) {
        setLetter(p.letter);
        setNumber(p.number);
    }

    // ------------------------------------------------------------

    public int getLetter() {
        return letter;
    }

    private void setLetter(int letter) {
        this.letter = letter;
    }

    public int getNumber() {
        return number;
    }

    private void setNumber(int number) {
        this.number = number;
    }

    // ------------------------------------------------------------

    /**
     * Exception for wrong {@link Position} parse format
     */
    public static class PositionFormatException extends IllegalArgumentException {
        public PositionFormatException(String msg) {
            super(msg);
        }
    }

    /**
     * Parse a string to a {@link Position}
     *
     * @param str
     *         String to parse
     *
     * @return Parsed {@link Position}
     *
     * @throws PositionFormatException
     *         if parsing fails
     */
    public static Position parsePosition(String str) throws PositionFormatException {
        int letter, number;
        try {
            letter = Character.toUpperCase(str.charAt(0)) - 'A' + 1;
            number = Integer.parseInt(str.substring(1));
        } catch (NullPointerException | IndexOutOfBoundsException | NumberFormatException e) {
            throw new PositionFormatException("Unable to parse position: " + str);
        }
        return new Position(letter, number);
    }

    @Override
    public String toString() {
        return "(" + (char) (getLetter() + 'A' - 1) + getNumber() + ")";
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getLetter()) ^ Integer.hashCode(getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Position))
            return false;
        Position p = (Position) o;
        return p.getLetter() == getLetter() && p.getNumber() == getNumber();
    }
}
