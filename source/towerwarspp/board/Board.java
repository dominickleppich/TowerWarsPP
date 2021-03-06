package towerwarspp.board;

import towerwarspp.io.Viewable;
import towerwarspp.io.Viewer;
import towerwarspp.player.ai.rating.RateStrategy;
import towerwarspp.preset.Move;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Position;
import towerwarspp.preset.Status;

import java.util.HashSet;
import java.util.Set;

import static towerwarspp.preset.PlayerColor.BLUE;
import static towerwarspp.preset.PlayerColor.RED;

/**
 * TowerWarsPP board class containing all game logic.
 * Created on 12.05.2017.
 *
 * @author dominick
 */
public class Board implements Viewable {
    private static final int TO_STRING_SPACE = 6;

    // ------------------------------------------------------------
    /**
     * Base positions
     */
    private final Position redBasePosition, blueBasePosition;
    /**
     * Board grid
     */
    private AppGrid grid;
    /**
     * Maximum Tower size
     */
    private int maxTowerSize;
    /**
     * Who's players turn is it?
     */
    private PlayerColor turn;
    /**
     * Possible moves for current player
     */
    private Set<Move> possibleMoves;
    /**
     * Status
     */
    private Status status;
    /**
     * Board viewer
     */
    private Viewer viewer;

    // ------------------------------------------------------------

    /**
     * Create a new board with side length {@code size}.
     *
     * @param size Size
     */
    public Board(int size) {
        if (size < 4 || size > 26)
            throw new IllegalArgumentException("size not in [4,26] (size == " + size + ")");
        grid = new AppGrid(size);

        // TODO better strategy
        this.maxTowerSize = size / 3;

        this.turn = RED;

        this.status = Status.OK;

        redBasePosition = new Position(1, 1);
        blueBasePosition = new Position(grid.getSize(), grid.getSize());

        initBoard();
    }

    public Board(Board b) {
        grid = new AppGrid(b.getSize());

        this.maxTowerSize = b.maxTowerSize;
        this.turn = b.turn;
        this.status = b.status;

        this.redBasePosition = b.redBasePosition;
        this.blueBasePosition = b.blueBasePosition;

        // Copy board
        for (int l = 1; l <= getSize(); l++) {
            for (int n = 1; n <= getSize(); n++) {
                Position p = new Position(l, n);
                Cell c = b.grid.getCell(p);
                grid.setCell(p, c == null ? null : c.clone());
            }
        }

        // Calculate possible moves
        possibleMoves = calculatePossibleMoves();
    }

    // ------------------------------------------------------------

    /**
     * Initialize the board with all player tokens and their bases.
     */
    private void initBoard() {
        // Empty all fields
        for (Position p : grid)
            grid.setCell(p, null);

        // Set player tokens
        for (Position p : grid.getRangePositions(redBasePosition,
                grid.getSize() / 2))
            grid.setCell(p, new Token(RED));

        for (Position p : grid.getRangePositions(blueBasePosition,
                grid.getSize() / 2))
            grid.setCell(p, new Token(BLUE));

        // Set bases
        grid.setCell(redBasePosition, new Base(RED));
        grid.setCell(blueBasePosition, new Base(BLUE));

        // Calculate possible moves
        possibleMoves = calculatePossibleMoves();
    }

    // ------------------------------------------------------------

    /**
     * Get the size of the board.
     *
     * @return Size
     */
    public int getSize() {
        return grid.getSize();
    }

    /**
     * Get the boards status.
     *
     * @return {@link Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Get players turn
     *
     * @return Active player
     */
    public PlayerColor getTurn() {
        return turn;
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
     * Get a move analyzer instance.
     *
     * @return {@link MoveAnalyzer}
     */
    public MoveAnalyzer getMoveAnalyzer() {
        return new MoveAnalyzer(this);
    }

    /**
     * Return a board viewer
     *
     * @return Board viewer
     */
    public Viewer viewer() {
        final Board board = this;
        // If viewer doesn't exist, create it
        if (viewer == null)
            viewer = new Viewer() {
                @Override
                public int getSize() {
                    return board.getSize();
                }

                @Override
                public Status getStatus() {
                    return board.getStatus();
                }

                @Override
                public PlayerColor getTurn() {
                    return board.getTurn();
                }

                @Override
                public Cell getCell(Position position) {
                    Cell cell = board.grid.getCell(position);
                    return cell == null ? null : cell.clone();
                }

                @Override
                public Set<Move> getPossibleMoves() {
                    return board.getPossibleMoves();
                }

                @Override
                public MoveAnalyzer getMoveAnalyzer() {
                    return new MoveAnalyzer(board);
                }

                @Override
                public int rateMove(RateStrategy strategy, Move move) {
                    // TODO clone?!
                    return strategy.rate(board, move);
                }

                @Override
                public String toString() {
                    return board.toString();
                }
            };

        // Return viewer
        return viewer;
    }

    // ------------------------------------------------------------

    // Helper functions

    /**
     * Count tokens owned by a player.
     *
     * @param color Player color
     * @return Number of owned tokens
     */
    public int getPlayerTokenCount(PlayerColor color) {
        int counter = 0;
        for (Position p : grid) {
            Cell c = grid.getCell(p);
            if (c != null && c instanceof Token && c.getColor() == color)
                counter++;
        }
        return counter;
    }

    /**
     * Count towers owned by a player.
     *
     * @param color Player color
     * @return Number of owned towers
     */
    public int getPlayerTowerCount(PlayerColor color) {
        int counter = 0;
        for (Position p : grid) {
            Cell c = grid.getCell(p);
            if (c != null && c instanceof Tower && c.getColor() == color)
                counter++;
        }
        return counter;
    }

    /**
     * Count tokens and towers owned by a player.
     *
     * @param color Player color
     * @return Number of owned tokens and towers
     */
    public int getPlayerPlayableCellCount(PlayerColor color) {
        int counter = 0;
        counter += getPlayerTokenCount(color);
        counter += getPlayerTowerCount(color);
        return counter;
    }

    public int distance(Position a, Position b) {
        return grid.distance(a, b);
    }

    public Position getRedBasePosition() {
        return redBasePosition;
    }

    public Position getBlueBasePosition() {
        return blueBasePosition;
    }

    /**
     * Checks if a given {@link Move} is valid in the current situation.
     *
     * @param move Move
     * @return true, if the move is valid
     */
    public boolean checkMove(Move move) {
        return getPossibleMoves().contains(move);
    }

    /**
     * Analyze if a move is valid or not. In case of illegal moves, give a
     * detailed description of the error.
     *
     * @param move Move
     * @return Analyze result of this move
     */
    public String analyzeMove(Move move) {
        if (move == null)
            return "Valid (null): Surrender move";

        Cell startCell = null, endCell = null;
        try {
            startCell = grid.getCell(move.getStart());
            endCell = grid.getCell(move.getEnd());
        } catch (IndexOutOfBoundsException e) {
            return "Invalid, Position out of board range";
        }

        // Empty cells cannot move
        if (startCell == null)
            return "Invalid, Empty cell cannot be moved";

        // Check correct player
        if (startCell.getColor() != getTurn())
            return "Invalid, Cell doesn't belong to the " +
                    "current player";

        if (move.getStart().equals(move.getEnd()))
            return "Invalid, Start and end position are equal";

        // Empty cells and bases cannot be moved
        if (startCell == null)
            return "Invalid, Start position is empty";
        if (startCell instanceof Base)
            return "Invalid, Base cannot be moved";

        // Check whether the move is a token or tower move
        if (startCell instanceof Token) {
            // Tokens simply move on empty cells
            if (endCell == null)
                return "Valid, Token moved on empty cell";

                // difference between colors
                // own color
            else if (startCell.getColor() == endCell.getColor()) {
                // Cannot move on own base
                if (endCell instanceof Base)
                    return "Invalid, Token cannot move on own base";
                    // Token on token is a new tower
                else if (endCell instanceof Token)
                    return "Valid, Token moved on own token "
                            + "and created new tower";
                    // Token on tower increase its size
                else if (endCell instanceof Tower) {
                    if (((Tower) endCell).isBlocked())
                        return "Valid, Token unblocked tower";
                    else if (((Tower) endCell).getHeight() < maxTowerSize)
                        return "Valid, Token moved on tower "
                                + "and increased size";
                    else
                        return "Invalid, Token tried to " +
                                "increase tower with max height";
                } else
                    return "Invalid, Token moved on unknown "
                            + "cell type";
            }
            // opponent
            else {
                // move on opponent base is win (=> allowed)
                if (endCell instanceof Base)
                    // base is kicked (win situation)
                    return "Valid, Token moved on opponent " +
                            "base -> WIN";
                    // Token on enemy token kicks it
                else if (endCell instanceof Token)
                    // simply move the token there
                    return "Valid, Token kicked opponent token";
                    // Token on tower kicks or blocks it
                else if (endCell instanceof Tower) {
                    // if move is melee, kick tower completely
                    if (grid.distance(move.getStart(), move.getEnd()) == 1)
                        return "Valid, Token kicked opponent "
                                + "tower with a " + "melee move";
                        // if move is ranged, block the tower
                    else {
                        if (((Tower) endCell).isBlocked())
                            return "Invalid, Token tried to " +
                                    "block already blocked tower";
                        else
                            return "Valid, Token blocked " +
                                    "opponent tower with a ranged move";
                    }
                } else
                    return "Invalid, Token moved on unknown "
                            + "cell type";
            }
        } else if (startCell instanceof Tower) {
            if (((Tower) startCell).isBlocked())
                return "Invalid, Blocked tower cannot move";

            // Towers cannot range move
            if (grid.distance(move.getStart(), move.getEnd()) > 1)
                return "Invalid, Towers cannot range move";

            // Towers simply move on empty cells
            if (endCell == null)
                return "Valid, Tower moved on empty cell";

                // Towers cannot move on enemy cells
            else if (startCell.getColor() != endCell.getColor())
                return "Invalid, Towers cannot kick opponent "
                        + "tokens or towers";

                // Tower on base is not allowed (neither own, nor enemy ->
                // cause towers cannot kick)
            else if (endCell instanceof Base)
                return "Invalid, Towers cannot move on bases";

                // Tower on token is new tower
            else if (endCell instanceof Token)
                return "Valid, Tower moved on own token and "
                        + "created new tower";

                // Tower on tower increase its height, if size not exceeded
            else if (endCell instanceof Tower) {
                if (((Tower) endCell).isBlocked())
                    return "Valid, Tower unblocked own tower";
                else if (((Tower) endCell).getHeight() < maxTowerSize)
                    return "Valid, Tower moved on another own " +
                            "tower and increased its height";
                else
                    return "Invalid, Tower tried to increase "
                            + "tower with max height";
            } else
                return "Invalid, Tower moved on unknown cell "
                        + "type";
        } else
            return "Invalid, Unknown start cell type";
    }

    /**
     * Apply the move on the board
     *
     * @param move Move
     */
    private void applyMove(Move move) {
        Cell startCell = grid.getCell(move.getStart()), endCell = grid.getCell(
                move.getEnd());

        if (move.getStart().equals(move.getEnd()))
            throw new IllegalMoveException("start == end");

        // Empty cells and bases cannot be moved
        if (startCell == null)
            throw new IllegalMoveException("empty cell moved");
        if (startCell instanceof Base)
            throw new IllegalMoveException("base moved");

        // Check whether the move is a token or tower move
        if (startCell instanceof Token) {
            // Remove token from start cell
            grid.setCell(move.getStart(), null);

            // Tokens simply move on empty cells
            if (endCell == null)
                grid.setCell(move.getEnd(), startCell);

                // difference between colors
                // own color
            else if (startCell.getColor() == endCell.getColor()) {
                // Cannot move on own base
                if (endCell instanceof Base)
                    throw new IllegalMoveException("moved on own base");
                    // Token on token is a new tower
                else if (endCell instanceof Token)
                    grid.setCell(move.getEnd(), new Tower(endCell.getColor()));
                    // Token on tower increase its size, if not blocked
                else if (endCell instanceof Tower) {
                    if (((Tower) endCell).isBlocked())
                        ((Tower) endCell).unblock();
                    else if (((Tower) endCell).getHeight() < maxTowerSize)
                        ((Tower) endCell).increase();
                    else
                        throw new IllegalMoveException("tower max height " +
                                "exceed");

                } else
                    throw new IllegalMoveException("unknown end cell type");
            }
            // opponent
            else {
                // move on opponent base is win (=> allowed)
                if (endCell instanceof Base)
                    // base is kicked (win situation)
                    grid.setCell(move.getEnd(), startCell);
                    // Token on enemy token kicks it
                else if (endCell instanceof Token)
                    // simply move the token there
                    grid.setCell(move.getEnd(), startCell);
                    // Token on tower kicks or blocks it
                else if (endCell instanceof Tower) {
                    // if move is melee, kick tower completely
                    if (grid.distance(move.getStart(), move.getEnd()) == 1)
                        grid.setCell(move.getEnd(), startCell);
                        // if move is ranged, block the tower
                    else if (((Tower) endCell).isBlocked())
                        throw new IllegalMoveException("opponent tower already blocked");
                    else
                        ((Tower) endCell).block();
                } else
                    throw new IllegalMoveException("unknown end cell type");
            }
        } else if (startCell instanceof Tower) {
            // Blocked towers cannot move!
            if (((Tower) startCell).isBlocked())
                throw new IllegalMoveException("blocked tower move");

            // Decrease tower if possible. Towers of height 1 become tokens
            if (((Tower) startCell).getHeight() == 1)
                grid.setCell(move.getStart(), new Token(startCell.getColor()));
            else
                ((Tower) startCell).decrease();

            // Towers cannot range move
            if (grid.distance(move.getStart(), move.getEnd()) > 1)
                throw new IllegalMoveException("tower cannot range move");

            // Towers simply move on empty cells
            if (endCell == null)
                grid.setCell(move.getEnd(), new Token(startCell.getColor()));

                // Towers cannot move on enemy cells
            else if (startCell.getColor() != endCell.getColor())
                throw new IllegalMoveException("tower move on opponent cell");

                // Tower on base is not allowed (neither own, nor enemy ->
                // cause towers cannot kick)
            else if (endCell instanceof Base)
                throw new IllegalMoveException("tower move on base");

                // Tower on token is new tower
            else if (endCell instanceof Token)
                grid.setCell(move.getEnd(), new Tower(startCell.getColor()));

                // Tower on tower increase its height, if size not exceeded
            else if (endCell instanceof Tower) {
                if (((Tower) endCell).isBlocked())
                    ((Tower) endCell).unblock();
                else if (((Tower) endCell).getHeight() < maxTowerSize)
                    ((Tower) endCell).increase();
                else
                    throw new IllegalMoveException("tower max height exceed");
            } else
                throw new IllegalMoveException("unknown end cell type");
        } else
            throw new IllegalMoveException("unknown start cell type");
    }

    /**
     * Make a move on the board.
     *
     * @param move Move
     * @return true, if move was made
     */
    public boolean makeMove(Move move) {
        // Moves can be made if and only if the status is ok!
        if (status != Status.OK)
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
        // 1. if red base was destroyed, blue player won
        if (!(grid.getCell(redBasePosition) instanceof Base))
            status = Status.BLUE_WIN;
        else if (!(grid.getCell(blueBasePosition) instanceof Base))
            status = Status.RED_WIN;

            // 2. if player cannot move, game is over
            // TODO change size() == 0 to size() == 1 (cause of always
            // possible null move)
            // remember! players turn already switched!!
        else if (possibleMoves.size() == 1) {
            if (turn == RED)
                status = Status.BLUE_WIN;
            else
                status = Status.RED_WIN;
        }

        return true;
    }

    // ------------------------------------------------------------

    // ------------------------------------------------------------

    /**
     * Calculate a set of all possible moves for the current player.
     *
     * @return Set of possible {@link Move}'s
     */
    private Set<Move> calculatePossibleMoves() {
        Set<Move> moves = new HashSet<>();

        // Iterate over the whole grid
        for (Position pos : grid) {
            // Get each cell and check which type of move to calculate
            Cell cell = grid.getCell(pos);

            // Only add moves for current player
            if (cell == null || cell.getColor() != getTurn())
                continue;

            if (cell instanceof Token)
                moves.addAll(getPossibleMovesForToken(pos));
            else if (cell instanceof Tower && !((Tower) cell).isBlocked())
                moves.addAll(getPossibleMovesForTower(pos));
        }

        // Add the surrender move
        // TODO add it again
        moves.add(null);

        return moves;
    }

    /**
     * Calculate the move range of a given position.
     * <p>
     * Empty cells and bases cannot move. Towers can move one field. Tokens can
     * move one field, their move range is increased by each direct non-blocked
     * own tower height.
     *
     * @param position Position
     * @return Range
     */
    private int calculateMoveRange(Position position) {
        Cell cell = grid.getCell(position);
        // Empty cells and bases don't move
        if (cell == null || cell instanceof Base)
            return 0;

        // Default token range
        int range = 1;

        // Tokens get bonus range
        if (cell instanceof Token) {
            // Calculate tower bonus
            // Towers must be direct neighbors, owned and non-blocked

            for (Position p : grid.getRangePositions(position, 1)) {
                Cell neighbor = grid.getCell(p);

                // If cell is a tower, add its size to own range
                if (neighbor instanceof Tower && neighbor.getColor() == cell.getColor() && !((Tower) neighbor)
                        .isBlocked())
                    range += ((Tower) neighbor).getHeight();
            }
        }

        return range;
    }

    /**
     * Calculate the set of all possible moves for a given token.
     *
     * @param position Position
     * @return Set of possible {@link Move}'s
     */
    private Set<Move> getPossibleMovesForToken(Position position) {
        PlayerColor tokenColor = grid.getCell(position).getColor();

        Set<Move> moves = new HashSet<>();

        int range = calculateMoveRange(position);

        // Tokens can move to all cells within its range (except the own base)
        for (Position p : grid.getRangePositions(position, range)) {
            // Skip moves to start position
            if (p.equals(position))
                continue;

            // Determine cell
            Cell cell = grid.getCell(p);

            // Tokens can always move on empty fields
            if (cell == null)
                moves.add(new Move(position, p));

                // Tokens cannot move on own base
            else if (cell.getColor() == tokenColor && cell instanceof Base)
                continue;

                // Tokens cannot move on own towers, if max height is
                // exceeded and tower not blocked
            else if (cell.getColor() == tokenColor && cell instanceof Tower
                    && ((Tower) cell)
                    .getHeight() + 1 > maxTowerSize && !((Tower) cell)
                    .isBlocked())
                continue;

                // Tokens cannot block already blocked enemy towers
            else if (cell.getColor() != tokenColor && cell instanceof Tower
                    && ((Tower) cell)
                    .isBlocked() && grid.distance(
                    position, p) > 1)
                continue;

            // All other cells are valid
            moves.add(new Move(position, p));
        }

        return moves;
    }

    /**
     * Calculate the set of all possible moves for a given tower
     *
     * @param position Position
     * @return Set of possible {@link Move}'s
     */
    private Set<Move> getPossibleMovesForTower(Position position) {
        PlayerColor towerColor = grid.getCell(position).getColor();

        Set<Move> moves = new HashSet<>();

        // Towers can only move to direct neighbors
        for (Position p : grid.getRangePositions(position, 1)) {
            // Determine cell
            Cell cell = grid.getCell(p);

            // Towers can always move on empty fields
            if (cell == null)
                moves.add(new Move(position, p));

                // Towers can never move on ememy cells
            else if (cell.getColor() != towerColor)
                continue;

                // Towers can always move on own tokens and create a tower
            else if (cell instanceof Token)
                moves.add(new Move(position, p));

                // Towers can move on own tower, if max tower size is not
                // exceeded
            else if (cell instanceof Tower && (((Tower) cell).isBlocked() || ((Tower) cell).getHeight() + 1 <= maxTowerSize))
                moves.add(new Move(position, p));
        }

        return moves;
    }

    /**
     * Switch player.
     */
    private void switchTurn() {
        turn = turn == RED ? BLUE : RED;
    }

    // ------------------------------------------------------------

    private static String fillSpaces(int n) {
        StringBuilder sb = new StringBuilder();
        while (n-- > 0)
            sb.append(" ");
        return sb.toString();
    }

    private static String fixedString(String s) {
        if (s.length() > TO_STRING_SPACE)
            return s.substring(0, TO_STRING_SPACE);
        return s + fillSpaces(TO_STRING_SPACE - s.length());
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(fillSpaces(TO_STRING_SPACE / 2));
        for (int l = 0; l < getSize(); l++)
            s.append(fixedString(Character.toString((char) (l + 'A'))));
        s.append("\n\n");
        for (int n = 1; n <= getSize(); n++) {
            s.append(fillSpaces(TO_STRING_SPACE / 2 * (n - 1)) + fixedString(
                    Integer.toString(n)));
            for (int l = 1; l <= getSize(); l++) {
                Cell cell = grid.getCell(new Position(l, n));
                String ss;
                if (cell instanceof Base)
                    ss = (cell.getColor() == RED ? "R" : "B") + "B";
                else if (cell instanceof Token)
                    ss = cell.getColor() == RED ? "R" : "B";
                else if (cell instanceof Tower) {
                    int size = Math.abs(((Tower) cell).getHeight());
                    ss = (cell.getColor() == RED ? "R" : "B") + "T" + size;
                } else
                    ss = ".";
                s.append(fixedString(ss));
            }
            s.append(fixedString(Integer.toString(n)) + "\n\n");
        }
        s.append(fillSpaces(TO_STRING_SPACE / 2 * (getSize() + 2)));
        for (int l = 0; l < getSize(); l++)
            s.append(fixedString(Character.toString((char) (l + 'A'))));
        return s.toString();
    }


    public Board clone() {
        return new Board(this);
    }
}
