package towerwarspp.board;

import eu.nepster.frozencube.game.grid.Grid;
import eu.nepster.frozencube.game.grid.GridCoordinate;
import eu.nepster.frozencube.game.grid.GridLogic;
import eu.nepster.frozencube.game.grid.hex.HexGrid;
import towerwarspp.preset.Move;
import towerwarspp.preset.Position;
import towerwarspp.preset.Status;

import java.util.HashSet;
import java.util.Set;

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
    // All grid coordinates
    private Set<GridCoordinate> gridCoordinates;
    // Game grid
    private Grid<Integer> grid;
    // Grid logic
    private GridLogic<Integer> gridLogic;
    // Board size
    private int size;
    // Tower size
    private int maxTowerSize;
    // Who's players turn is it?
    private int turn;
    // Status
    private Status status;

    // ------------------------------------------------------------

    /**
     * Create a new board with side length {@code size}.
     *
     * @param size
     *         Size
     */
    public Board(int size, int maxTowerSize) {
        grid = new HexGrid<>();
        gridLogic = grid.getLogic();

        gridCoordinates = new HashSet<>();

        // Add grid coordinates
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                GridCoordinate c = new GridCoordinate(x, y);
                gridCoordinates.add(c);
                grid.add(c);
                grid.setData(c, 0);
            }
        }

        this.size = size;
        this.maxTowerSize = maxTowerSize;
        this.turn = RED;

        this.status = Status.OK;

        redBaseCoordinate = new GridCoordinate(0, 0);
        blueBaseCoordinate = new GridCoordinate(size - 1, size - 1);

        initBoard();
    }

    // ------------------------------------------------------------

    /**
     * Initialize the board with all player tokens and their bases.
     */
    private void initBoard() {
        for (GridCoordinate c : gridLogic.getRange(redBaseCoordinate, size - 3))
            grid.setData(c, RED);

        for (GridCoordinate c : gridLogic.getRange(blueBaseCoordinate, size - 3))
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
     *
     * @return true, if move was made
     */
    public boolean makeMove(Move move) {
        // Moves can be made if and only if the status is ok!
        if (!status.isOK())
            return false;

        // check if move is valid
        if (!checkMove(move)) {
            status = Status.ILLEGAL;
            return false;
        }

        // check for null move, player surrender
        if (move == null) {
            // other player wins
            if (getTurn() == RED)
                status = Status.BLUE_WIN;
            else
                status = Status.RED_WIN;
            return true;
        }

        // TODO check win situation

        // apply move
        applyMove(move);

        // switch player
        switchTurn();

        return true;
    }

    /**
     * Checks if a given {@link Move} is valid in the current situation.
     *
     * @param move
     *         Move
     *
     * @return true, if the move is valid
     */
    public boolean checkMove(Move move) {
        // TODO can be improved
        return getPossibleMoves().contains(move);
    }

    /**
     * Apply the move on the board
     *
     * @param move
     *         Move
     */
    private void applyMove(Move move) {
        int oldStartData = grid.getData(positionToGridCoordinate(move.getStart()));
        int oldEndData = grid.getData(positionToGridCoordinate(move.getEnd()));

        // TODO extra rule for tower attacks missing
        grid.setData(positionToGridCoordinate(move.getStart()), oldStartData - getTurn());
        grid.setData(positionToGridCoordinate(move.getEnd()), oldEndData + getTurn());
    }

    /**
     * Switch player at the end of his turn.
     */
    private void switchTurn() {
        turn *= -1;
    }

    /**
     * Calculate a set of all possible moves for the current player.
     *
     * @return Set of possible {@link Move}'s
     */
    public Set<Move> getPossibleMoves() {
        Set<Move> moves = new HashSet<>();

        for (GridCoordinate c : gridCoordinates)
            if (grid.getData(c) == getTurn())
                moves.addAll(getPossibleMovesForToken(c));

        moves.add(null);

        return moves;
    }

    /**
     * Converts {@link Position} to {@link GridCoordinate}
     *
     * @param position
     *         Position
     *
     * @return Position
     */
    private GridCoordinate positionToGridCoordinate(Position position) {
        return new GridCoordinate(position.getX(), position.getY());
    }

    /**
     * Determine the current player
     *
     * @return {@code RED} or {@code BLUE}
     */
    private int getTurn() {
        return turn;
    }

    /**
     * Get the boards status.
     *
     * @return {@link Status}
     */
    public Status getStatus() {
        return status;
    }

    // ------------------------------------------------------------

    // Helper functions

    /**
     * Calculate the set of all possible moves for a given token.
     *
     * @param coordinate
     *         Coordinate
     *
     * @return Set of possible {@link Move}'s
     */
    private Set<Move> getPossibleMovesForToken(GridCoordinate coordinate) {
        Set<Move> moves = new HashSet<>();

        // TODO tower range increasing missing
        for (GridCoordinate c : gridLogic.getBorderNeighbors(coordinate))
            moves.add(new Move(gridCoordinateToPosition(coordinate), gridCoordinateToPosition(c)));

        return moves;
    }

    /**
     * Converts {@link GridCoordinate} to {@link Position}
     *
     * @param coordinate
     *         Coordinate
     *
     * @return Position
     */
    private Position gridCoordinateToPosition(GridCoordinate coordinate) {
        return new Position(coordinate.getX(), coordinate.getY());
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        //return grid.toString();
        StringBuilder s = new StringBuilder();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Integer value = grid.getData(new GridCoordinate(x, y));
                s.append(value);
                s.append("\t");
                if (value != null && value.toString().length() < 4)
                    s.append("\t");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
