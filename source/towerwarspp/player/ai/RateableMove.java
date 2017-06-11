package towerwarspp.player.ai;

import towerwarspp.preset.Move;

/**
 * A rateable move class
 *
 * @author Dominick Leppich
 */
public class RateableMove implements Rateable, Comparable<RateableMove> {
    private Move move;
    private RateStrategy strategy;

    // ------------------------------------------------------------

    public RateableMove(Move move, RateStrategy strategy) {
        this.move = move;
        this.strategy = strategy;
    }

    // ------------------------------------------------------------

    public Move getMove() {
        return move;
    }

    @Override
    public int rate() {
        return strategy.rate(move);
    }

    @Override
    public int compareTo(RateableMove rateableMove) {
        return new Integer(rate()).compareTo(new Integer(rateableMove.rate()));
    }
}
