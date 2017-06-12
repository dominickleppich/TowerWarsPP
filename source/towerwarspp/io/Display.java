package towerwarspp.io;

import towerwarspp.preset.Move;

/**
 * Interface output classes must implement in order to display a match. At
 * the beginning of the match the output class will get a viewer of board of
 * the running game.
 *
 * @author Dominick Leppich
 */
public interface Display {
    /**
     * Set the viewer for the display.
     *
     * @param viewer
     *         Viewer
     */
    void setViewer(Viewer viewer);

    /**
     * Update the display with the move just made.
     *
     * @param move
     *         Move
     */
    void update(Move move);
}
