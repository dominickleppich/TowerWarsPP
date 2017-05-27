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

    public Position(int x, int y) {
        setLetter(x);
        setNumber(y);
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
