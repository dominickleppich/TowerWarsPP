package towerwarspp.output;

import towerwarspp.preset.Move;

/**
 * Viewer interface to show the board.
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public interface Viewer {
    /**
     * Get the size of the board
     *
     * @return Size
     */
    int getSize();

    /**
     * Get the field value at the given position
     *
     * @param letter
     *         Letter
     * @param number
     *         Number
     *
     * @return Field value
     */
    int getField(int letter, int number);

    /**
     * Check if a given move is valid at the moment
     *
     * @param move
     *         Move
     *
     * @return true, if the move is valid
     */
    boolean checkMove(Move move);
}
