package towerwarspp.player.ai.better;

import towerwarspp.player.ai.rating.BestRatedMoveAI;

/**
 * AI using improved rating function
 *
 * @author Dominick Leppich
 */
public class BetterRatingAI extends BestRatedMoveAI {
    public BetterRatingAI() {
        super(new BetterRatingStrategy());
    }
}
