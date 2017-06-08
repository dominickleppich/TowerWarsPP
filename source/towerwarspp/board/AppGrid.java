package towerwarspp.board;

import eu.nepster.frozencube.game.grid.Grid;
import eu.nepster.frozencube.game.grid.GridCoordinate;
import eu.nepster.frozencube.game.grid.GridLogic;
import eu.nepster.frozencube.game.grid.hex.HexGrid;
import towerwarspp.preset.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 08.06.2017.
 *
 * @author dominick
 */
public class AppGrid {
    /**
     * Grid
     */
    private Grid<Cell> grid;
    /**
     * Grid logic
     */
    private GridLogic<Cell> gridLogic;
    /**
     * All grid coordinates
     */
    private Set<GridCoordinate> gridCoordinates;
    /**
     * Size
     */
    private int size;

    // ------------------------------------------------------------

    /**
     * Create a new App grid.
     *
     * @param size
     *         size
     */
    public AppGrid(int size) {
        grid = new HexGrid<>();
        gridLogic = grid.getLogic();

        gridCoordinates = new HashSet<>();

        // Add grid coordinates
        for (int l = 1; l <= size; l++) {
            for (int n = 1; n <= size; n++) {
                GridCoordinate c = new GridCoordinate(l, n);
                gridCoordinates.add(c);
                grid.add(c);
            }
        }

        this.size = size;
    }

    // ------------------------------------------------------------

    /**
     * Get the size of the grid.
     *
     * @return Size
     */
    public int getSize() {
        return size;
    }

    // ------------------------------------------------------------

    /**
     * Get cell at given {@link towerwarspp.preset.Position}.
     *
     * @param position
     *         Position
     *
     * @return Cell
     */
    public Cell getCell(Position position) {
        if (position == null)
            throw new IllegalArgumentException("position == null");

        return grid.getData(new GridCoordinate(position.getLetter(), position.getNumber()));
    }

    /**
     * Set a cell to a given value.
     *
     * @param position
     *         Position of the cell to chance
     * @param c
     *         New cell value
     */
    public void setCell(Position position, Cell c) {
        if (position == null)
            throw new IllegalArgumentException("position == null");

        grid.setData(new GridCoordinate(position.getLetter(), position.getNumber()), c);
    }
}
