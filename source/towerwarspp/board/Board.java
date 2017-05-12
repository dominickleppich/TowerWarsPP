package towerwarspp.board;

import eu.nepster.frozencube.game.grid.Grid;
import eu.nepster.frozencube.game.grid.GridCoordinate;
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

    private static final int RED_BASE = RED * 10000;
    private static final int BLUE_BASE = BLUE * 10000;

    // ------------------------------------------------------------
    // Bases
    private final GridCoordinate redBaseCoordinate, blueBaseCoordinate;
    // Game grid
    private Grid<Integer> grid;
    // Board size
    private int size;
    // Tower size
    private int maxTowerSize;
    // Who's players turn is it?
    private int turn;

    // ------------------------------------------------------------

    /**
     * Create a new board with side length {@code size}.
     *
     * @param size
     *         Size
     */
    public Board(int size, int maxTowerSize) {
        grid = new HexGrid<>();

        // Add grid coordinates
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                GridCoordinate c = new GridCoordinate(x, y);
                grid.add(c);
                grid.setData(c, 0);
            }
        }

        this.size = size;
        this.maxTowerSize = maxTowerSize;
        this.turn = RED;

        redBaseCoordinate = new GridCoordinate(0, 0);
        blueBaseCoordinate = new GridCoordinate(size - 1, size - 1);

        initBoard();
    }

    // ------------------------------------------------------------

    /**
     * Initialize the board with all player tokens and their bases.
     */
    private void initBoard() {
        for (GridCoordinate c : grid.getLogic().getRange(redBaseCoordinate, size - 3))
            grid.setData(c, RED);

        for (GridCoordinate c : grid.getLogic().getRange(blueBaseCoordinate, size - 3))
            grid.setData(c, BLUE);

        grid.setData(redBaseCoordinate, RED_BASE);
        grid.setData(blueBaseCoordinate, BLUE_BASE);
    }

    // ------------------------------------------------------------

    /**
     * Make a move on the board.
     *
     * @param move
     *         Move
     */
    public void makeMove(Move move) {
        // TODO
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        //return grid.toString();
        StringBuilder s = new StringBuilder();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Integer value = grid.getData(new GridCoordinate(x, y));
                s.append(value + "\t");
                if (value != null && value.toString().length() < 4)
                    s.append("\t");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
