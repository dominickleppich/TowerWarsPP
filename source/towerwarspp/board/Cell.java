package towerwarspp.board;

import towerwarspp.preset.PlayerColor;

/**
 * Board cell.
 * Created by dominick on 5/29/17.
 */
public abstract class Cell {
    private PlayerColor color;

    // --

    public Cell(PlayerColor color) {
        this.color = color;
    }

    // --

    /**
     * Get the color of the cell.
     *
     * @return PlayerColor
     */
    PlayerColor getColor() {
        return color;
    }
}
