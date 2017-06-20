package towerwarspp.player;

import towerwarspp.Boot;
import towerwarspp.board.Board;
import towerwarspp.io.Viewer;
import towerwarspp.preset.Move;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Status;

/**
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public abstract class AbstractPlayer implements Player {
    /**
     * Player Board
     */
    protected Board board;
    /**
     * Player method call order state
     */
    private State state;

    // ------------------------------------------------------------

    @Override
    public Move request() throws Exception {
        if (state != State.REQUEST)
            throw new Exception("Player methods called in wrong order! Called" + " request() but " +
                    "expected: " + state);

        Move move = deliver();
        board.makeMove(move);
        state = State.CONFIRM;

        if (Boot.isDebug())
            System.out.println("Player method request: move = " + move);

        return move;
    }

    @Override
    public void confirm(Status boardStatus) throws Exception {
        if (state != State.CONFIRM)
            throw new Exception("Player methods called in wrong order! Called" + " confirm() but " +
                    "expected: " + state);

        if (!board.getStatus().equals(boardStatus))
            throw new Exception("Board status mismatch! Player board has " + "status " + board.getStatus() + " and" + " game " + "board has status " + boardStatus);

        state = State.UPDATE;

        if (Boot.isDebug())
            System.out.println("Player method confirm: boardStatus = " + boardStatus);
    }

    @Override
    public void update(Move opponentMove, Status boardStatus) throws Exception {
        if (state != State.UPDATE)
            throw new Exception("Player methods called in wrong order! Called" + " update() but " +
                    "expected: " + state);

        board.makeMove(opponentMove);

        if (!board.getStatus().equals(boardStatus))
            throw new Exception("Board status mismatch! Player board has " + "status " + board.getStatus() + " and" + " game " + "board has status " + boardStatus);

        state = State.REQUEST;

        if (Boot.isDebug())
            System.out.println("Player method update: opponentMove = " + opponentMove + ", boardStatus = " + boardStatus);
    }

    @Override
    public void init(int size, PlayerColor color) throws Exception {
        board = new Board(size);
        if (color == PlayerColor.RED)
            state = State.REQUEST;
        else
            state = State.UPDATE;

        if (Boot.isDebug())
            System.out.println("Player initialized with size " + size + " and color " + color);
    }

    public abstract Move deliver() throws Exception;

    /**
     * Return a viewer of the player board.
     *
     * @return Viewer
     */
    public Viewer getViewer() {
        return board.viewer();
    }

    // ------------------------------------------------------------

    /**
     * Method call order state
     */
    private enum State {
        REQUEST, CONFIRM, UPDATE
    }
}
