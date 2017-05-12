package towerwarspp.board;

import eu.nepster.frozencube.game.grid.Grid;
import eu.nepster.frozencube.game.grid.hex.HexGrid;
import towerwarspp.preset.Move;

/**
 * TowerWarsPP board class containing all game logic.
 * Created on 12.05.2017.
 *
 * @author dominick
 */
public class Board {
    private static final int RED = 1;
    private static final int BLUE = -1;

    // ------------------------------------------------------------

    // Game grid
    private Grid<Integer> grid;

    private int maxTowerSize;

    // ------------------------------------------------------------

    /**
     * Create a new board with side length {@code size}.
     *
     * @param size
     *         Size
     */
    public Board(int size, int maxTowerSize) {
        grid = new HexGrid<>();

        this.maxTowerSize = maxTowerSize;
    }

    // ------------------------------------------------------------

    /**
     * Make a move on the board.
     *
     * @param move
     *         Move
     */
    public void makeMove(Move move) {

    }
}
