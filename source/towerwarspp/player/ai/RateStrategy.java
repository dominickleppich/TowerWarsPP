package towerwarspp.player.ai;

import towerwarspp.preset.Move;

/**
 * Created on 11.06.2017.
 *
 * @author Dominick Leppich
 */
public interface RateStrategy {
    int rate(Move move);
}
