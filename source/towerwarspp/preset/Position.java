package towerwarspp.preset;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    private int letter, number;

    // ------------------------------------------------------------

    public Position(int letter, int number) {
        setLetter(letter);
        setNumber(number);
    }

    public Position(Position position) {
        if (position == null)
            throw new IllegalArgumentException("position == null");

        setLetter(position.getLetter());
        setNumber(position.getNumber());
    }

    // ------------------------------------------------------------

    public int getLetter() {
        return letter;
    }

    private void setLetter(int letter) {
        if (letter <= 0 || letter > 26)
            throw new IllegalArgumentException("letter " + letter + " out of range!");

        this.letter = letter;
    }

    public int getNumber() {
        return number;
    }

    private void setNumber(int number) {
        if (number <= 0 || number > 26)
            throw new IllegalArgumentException("number " + number + " out of range!");

        this.number = number;
    }

    // ------------------------------------------------------------

    public static Position parsePosition(String str)
            throws IllegalArgumentException, PositionFormatException {

        if (str == null)
            throw new IllegalArgumentException("str == null");

        try {
            return new Position(Character.toUpperCase(str.charAt(0)) - 'A' + 1,
                                       Integer.parseInt(str.substring(1)));
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new PositionFormatException("Error parsing: \"" + str + "\"", e);
        }
    }

    @Override
    public String toString() {
        return "" + (char) (getLetter() + 'A' - 1) + getNumber();
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
