package towerwarspp.player;

import towerwarspp.input.Requestable;
import towerwarspp.preset.Move;

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
        return requestable.request();
    }
}
