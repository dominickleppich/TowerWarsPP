package towerwarspp.output;

import towerwarspp.board.Cell;
import towerwarspp.board.MoveAnalyzer;
import towerwarspp.preset.Move;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Position;
import towerwarspp.preset.Status;

import java.util.Set;

/**
 * Viewer interface to show the board.
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public interface Viewer {
    /**
     * Get the size of the board.
     *
     * @return Size
     */
    int getSize();

    /**
     * Get the current board status.
     *
     * @return Status
     */
    Status getStatus();

    /**
     * Get active player.
     *
     * @return Active player
     */
    PlayerColor getTurn();

    /**
     * Get the cell at the given position.
     *
     * @param position
     *         Position
     *
     * @return Cell
     */
    Cell getCell(Position position);

    /**
     * Get a set of all possible moves.
     *
     * @return Set of possible moves
     */
    Set<Move> getPossibleMoves();

    /**
     * Get a move analyzing instance.
     *
     * @return {@link MoveAnalyzer}
     */
    MoveAnalyzer getMoveAnalyzer();
}
