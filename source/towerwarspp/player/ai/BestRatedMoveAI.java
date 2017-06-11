package towerwarspp.player.ai;

import towerwarspp.player.AbstractPlayer;
import towerwarspp.preset.Move;

/**
 * This AI choses the best rated move.
 *
 * @author Dominick Leppich
 */
public class BestRatedMoveAI extends AbstractPlayer {
    @Override
    public Move deliver() throws Exception {
        RateStrategy strategy = new SimpleStrategy(board);

        // Rate all moves and keep the maximum
        RateableMove move = null;
        for (Move m : board.getPossibleMoves()) {
            System.out.println("Possible move " + m);
            RateableMove tmp = new RateableMove(m, strategy);
            if (move == null || move.compareTo(tmp) < 0)
                move = tmp;
        }

        return move == null ? null : move.getMove();
    }
}
