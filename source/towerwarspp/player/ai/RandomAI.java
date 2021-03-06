package towerwarspp.player.ai;

import towerwarspp.player.AbstractPlayer;
import towerwarspp.preset.Move;

import java.util.Iterator;
import java.util.Set;

/**
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public class RandomAI extends AbstractPlayer {
    private static java.util.Random rnd;

    static {
        rnd = new java.util.Random(System.currentTimeMillis());
    }

    // ------------------------------------------------------------

    /**
     * Get a random move from a set of moves.
     *
     * @param moves
     *         Set of moves
     *
     * @return Randomly selected move from the set
     *
     * @throws Exception
     *         if the set of moves is empty
     */
    public static Move getRandomMoveFromSet(Set<Move> moves) throws Exception {
        try {
            int i;
            Move m = null;

            // AI never surrenders
            do {
                i = rnd.nextInt(moves.size()) + 1;

                Iterator<Move> it = moves.iterator();
                while (i-- > 0)
                    m = it.next();
            } while (m == null);

            return m;
        } catch (IllegalArgumentException e) {
            throw new Exception("No valid move to randomly select!");
        }
    }

    // ------------------------------------------------------------

    @Override
    public Move deliver() throws Exception {
        return getRandomMoveFromSet(board.getPossibleMoves());
    }
}
