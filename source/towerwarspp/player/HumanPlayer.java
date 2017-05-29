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
    private String name;

    // ------------------------------------------------------------

    public HumanPlayer(Requestable requestable, String name) {
        this.requestable = requestable;
        this.name = name;
    }

    // ------------------------------------------------------------

    @Override
    public Move deliver() throws Exception {
        return requestable.request(board.viewer());
    }
}
