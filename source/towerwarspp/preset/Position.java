package towerwarspp.preset;

import java.io.Serializable;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public class Position implements Serializable {
    private int letter;
    private int number;

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

    public void setLetter(int letter) {
        this.letter = letter;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        return "" + ('A' + letter) + (number + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Position))
            return false;
        Position p = (Position) o;
        return p.letter == letter && p.number == number;
    }
}
