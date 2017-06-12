package towerwarspp.player.ai.rating;

import towerwarspp.preset.Move;

/**
 * A rateable move class
 *
 * @author Dominick Leppich
 */
public class RateableMove implements Comparable<RateableMove> {
    private Move move;
    private int rating;

    // ------------------------------------------------------------

    public RateableMove(Move move, int rating) {
        this.move = move;
        this.rating = rating;
    }

    // ------------------------------------------------------------

    public Move getMove() {
        return move;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public int compareTo(RateableMove rateableMove) {
        return new Integer(getRating()).compareTo(new Integer(rateableMove.getRating()));
    }
}
