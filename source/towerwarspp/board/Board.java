package towerwarspp.board;

import eu.nepster.frozencube.game.grid.Grid;
import eu.nepster.frozencube.game.grid.GridCoordinate;
import eu.nepster.frozencube.game.grid.GridLogic;
import eu.nepster.frozencube.game.grid.hex.HexGrid;
import towerwarspp.preset.Move;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Position;
import towerwarspp.preset.Status;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

/**
 * TowerWarsPP board class containing all game logic.
 * Created on 12.05.2017.
 *
 * @author dominick
 */
public class Board extends Observable {
    private static final int RED = 1;
    private static final int BLUE = -1;

    private static final int BASE = 100;
    private static final int RED_BASE = RED * BASE;
    private static final int BLUE_BASE = BLUE * BASE;

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
    // Possible moves for current player
    private Set<Move> possibleMoves;
    // Status
    private Status status;

    // ------------------------------------------------------------

    /**
     * Create a new board with side length {@code size}.
     *
     * @param size
     *         Size
     * @param maxTowerSize
     *         max tower size
     */
    public Board(int size, int maxTowerSize) {
        grid = new HexGrid<>();
        gridLogic = grid.getLogic();

        gridCoordinates = new HashSet<>();

        // Add grid coordinates
        for (int l = 1; l <= size; l++) {
            for (int n = 1; n <= size; n++) {
                GridCoordinate c = new GridCoordinate(l, n);
                gridCoordinates.add(c);
                grid.add(c);
                grid.setData(c, 0);
            }
        }

        this.size = size;
        this.maxTowerSize = maxTowerSize;
        this.turn = RED;

        this.status = Status.OK;

        redBaseCoordinate = new GridCoordinate(1, 1);
        blueBaseCoordinate = new GridCoordinate(size, size);

        initBoard();
    }

    // ------------------------------------------------------------

    /**
     * Initialize the board with all player tokens and their bases.
     */
    private void initBoard() {
        for (GridCoordinate c : gridLogic.getRange(redBaseCoordinate, size / 3))
            grid.setData(c, RED);

        for (GridCoordinate c : gridLogic.getRange(blueBaseCoordinate, size / 3))
            grid.setData(c, BLUE);

        grid.setData(redBaseCoordinate, RED_BASE);
        grid.setData(blueBaseCoordinate, BLUE_BASE);

        // Calculate possible moves
        possibleMoves = calculatePossibleMoves();
    }

    // ------------------------------------------------------------

    /**
     * Get the boards status.
     *
     * @return {@link Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Get the calculated set of possible moves for the current player.
     *
     * @return Set of possible {@link Move}'s
     */
    public Set<Move> getPossibleMoves() {
        return possibleMoves;
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
            if (turn == RED)
                status = Status.BLUE_WIN;
            else
                status = Status.RED_WIN;
            return true;
        }

        // apply move
        applyMove(move);

        // switch player
        switchTurn();

        // Calculate possible moves
        possibleMoves = calculatePossibleMoves();

        // Check win condition ...
        // 1. if base was destroyed, player won
        if (positionToGridCoordinate(move.getEnd()).equals(redBaseCoordinate))
            status = Status.BLUE_WIN;
        else if (positionToGridCoordinate(move.getEnd()).equals(blueBaseCoordinate))
            status = Status.RED_WIN;

        // 2. if player cannot move, game is over
        // TODO change size() == 0 to size() == 1 (cause of always possible null move)
        // remember! players turn already switched!!
        else if (possibleMoves.size() == 0) {
            if (turn == RED)
                status = Status.BLUE_WIN;
            else
                status = Status.RED_WIN;
        }

        // Notify observers
        setChanged();
        notifyObservers(move);
        clearChanged();

        return true;
    }

    // ------------------------------------------------------------

    // Viewer functions

    /**
     * Get the size of the board
     *
     * @return Size
     */
    public int getSize() {
        return size;
    }

    public PlayerColor getTurn() {
        return turn == RED ? PlayerColor.RED : PlayerColor.BLUE;
    }

    // ------------------------------------------------------------

    // Helper functions

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
        grid.setData(positionToGridCoordinate(move.getStart()), oldStartData - turn);
        grid.setData(positionToGridCoordinate(move.getEnd()), oldEndData + turn);
    }

    /**
     * Switch player at the end of his turn.
     */
    private void switchTurn() {
        turn *= -1;
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
        return new GridCoordinate(position.getLetter(), position.getNumber());
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
     * Calculate a set of all possible moves for the current player.
     *
     * @return Set of possible {@link Move}'s
     */
    private Set<Move> calculatePossibleMoves() {
        Set<Move> moves = new HashSet<>();

        // TODO fails if only towers exist
        for (GridCoordinate c : gridCoordinates)
            if (grid.getData(c) == turn)
                moves.addAll(getPossibleMovesForToken(c));

        //moves.add(null);

        return moves;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        //return grid.toString();
        StringBuilder s = new StringBuilder();
        for (int n = 1; n <= size; n++) {
            for (int l = 1; l <= size; l++) {
                Integer value = grid.getData(new GridCoordinate(l, n));
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
