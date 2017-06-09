package towerwarspp.board;

import towerwarspp.preset.Move;

/**
 * A MoveAnalyzer is able to give a detailed description of what a move is
 * about to do in a fixed Board scenario. The
 * description is returned as a displayable string.
 * Moves are either "Valid" or "Invalid", followed by a reason.
 *
 * @author dominick
 */
public class MoveAnalyzer {
    private Board board;

    // ------------------------------------------------------------

    /**
     * Ctr
     *
     * @param board
     *         Board
     */
    public MoveAnalyzer(Board board) {
        this.board = board;
    }

    // ------------------------------------------------------------

    /**
     * Analyze the given move
     *
     * @param move
     *         Move
     *
     * @return Analyze result
     */
    public String analyzeMove(Move move) {
        return board.analyzeMove(move);
    }
}
