package towerwarspp.player;

import towerwarspp.io.graphic.java2d.Java2DFrame;
import towerwarspp.player.ai.SimpleStrategy;
import towerwarspp.preset.Move;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Requestable;

/**
 * Human player who can be requested for moves.
 *
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public class HumanPlayer extends AbstractPlayer {
    private Requestable requestable;

    // ------------------------------------------------------------

    public HumanPlayer(Requestable requestable) {
        this.requestable = requestable;
    }

    // ------------------------------------------------------------

    @Override
    public Move deliver() throws Exception {
        return requestable.deliver();
    }

    // ------------------------------------------------------------

    @Override
    public void init(int size, PlayerColor color) throws Exception {
        super.init(size, color);

        if (requestable instanceof Java2DFrame)
            ((Java2DFrame) requestable).setRatingStrategy(new SimpleStrategy(board));
    }
}
