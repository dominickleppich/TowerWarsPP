package towerwarspp.player.ai.simple;

import towerwarspp.player.ai.rating.BestRatedMoveAI;

/**
 * SimpleAI described in the project description
 *
 * @author Dominick Leppich
 */
public class SimpleAI extends BestRatedMoveAI {

    public SimpleAI() {
        super(new SimpleStrategy());
    }
}
