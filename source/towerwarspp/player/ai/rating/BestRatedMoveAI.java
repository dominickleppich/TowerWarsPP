package towerwarspp.player.ai.rating;

import towerwarspp.player.AbstractPlayer;
import towerwarspp.player.ai.simple.SimpleStrategy;
import towerwarspp.preset.Move;

/**
 * This AI choses the best rated move.
 *
 * @author Dominick Leppich
 */
public class BestRatedMoveAI extends AbstractPlayer {
    private RateStrategy strategy;

    // ------------------------------------------------------------

    public BestRatedMoveAI(RateStrategy strategy) {
        this.strategy = strategy;
    }

    // ------------------------------------------------------------

    @Override
    public Move deliver() throws Exception {
        // Rate all moves and keep the maximum
        RateableMove move = null;
        for (Move m : board.getPossibleMoves()) {
            RateableMove tmp = new RateableMove(m, strategy.rate(board, m));
            if (move == null || move.compareTo(tmp) < 0)
                move = tmp;
        }

        return move == null ? null : move.getMove();
    }
}
