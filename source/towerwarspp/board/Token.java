package towerwarspp.board;

import towerwarspp.preset.PlayerColor;

/**
 * Created by dominick on 5/29/17.
 */
public class Token extends Cell {
    public Token(PlayerColor color) {
        super(color);
    }

    @Override
    public Cell clone() {
        return new Token(getColor());
    }
}
