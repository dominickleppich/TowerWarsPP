package towerwarspp.game;

import towerwarspp.Boot;
import towerwarspp.board.Board;
import towerwarspp.io.InputOutputable;
import towerwarspp.io.TextIO;
import towerwarspp.preset.Move;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Status;

/**
 * This class handles a single match. It requests, confirms and updates moves
 * of both players, initializes them
 * correctly and terminates after the game has finished.
 * The game runs in an own thread. It is possible to wait for the match to
 * terminate.
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public class Match implements Runnable {
    /**
     * Thread the match is running in
     */
    private Thread matchThread;
    /**
     * The match board
     */
    private Board board;
    /**
     * Array of players. Red player is the first in the array
     */
    private Player[] player;
    /**
     * Board size
     */
    private int size;
    /**
     * Flag for checking of the match was initialized
     */
    private boolean initialized = false;
    /**
     * Board observer
     */
    private InputOutputable inout;

    // ------------------------------------------------------------

    /**
     * Create a new match with given red and blue players and specifying the
     * boards size.
     *
     * @param red
     *         Red player
     * @param blue
     *         Blue player
     * @param size
     *         Board size
     * @param inout
     *         In- and output of the game
     */
    public Match(Player red, Player blue, int size, InputOutputable inout) {
        player = new Player[] {red, blue};
        this.size = size;
        board = new Board(size);
        this.inout = inout;
    }

    // ------------------------------------------------------------

    /**
     * Starts the match.
     *
     * @throws MatchException
     *         if the game could not be started
     */
    public void start() throws MatchException {
        if (matchThread != null)
            throw new MatchException("match already running");
        if (!initialized)
            throw new MatchException("match is not initialized");

        matchThread = new Thread(this);
        matchThread.start();
    }

    /**
     * Stop the game
     */
    @SuppressWarnings("deprecation")
    public void stop() {
        matchThread.stop();
    }

    /**
     * Wait for match to end
     */
    public synchronized void waitMatch() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------

    /**
     * Initialize the match (and the players)
     *
     * @throws MatchException
     *         if the game is already initialized
     * @throws Exception
     *         if initializing players fails
     */
    public void init() throws MatchException, Exception {
        if (initialized)
            throw new RuntimeException("match already initialized");

        player[0].init(size, PlayerColor.RED);
        player[1].init(size, PlayerColor.BLUE);
        initialized = true;

        // Set viewer
        inout.setViewer(board.viewer());
    }

    /**
     * The main game loop. The match requests moves from both players and
     * confirms and updates them accordingly.
     * Exceptions are handled as lose.
     */
    @Override
    public void run() {
        int moveCounter = 0;

        Status matchStatus = Status.OK;

        while (matchStatus == Status.OK) {
            try {
                int delay = Boot.getDelay();
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Request move
            Move m = null;
            try {
                m = player[moveCounter % 2].request();
            } catch (Exception e) {
                System.err.println(
                        (moveCounter % 2 == 0 ? "Red" : "Blue") + " failed " + "to" + " request! (" + e.getMessage()
                                + ")");
                matchStatus = (moveCounter % 2 == 0 ? Status.BLUE_WIN : Status.RED_WIN);
                continue;
            }

            boolean success = board.makeMove(m);

            if (Boot.isDebug()) {
                System.out.println("LOG Move No " + (moveCounter + 1) + ": " + m + " " + (success ?
                                                                                                  "succeeded" : "failed"));
                if (!(inout instanceof TextIO))
                    System.out.println(board);
            }


            // Update display
            inout.update(m);

            // Confirm move
            try {
                player[moveCounter % 2].confirm(board.getStatus());
            } catch (Exception e) {
                System.err.println(
                        (moveCounter % 2 == 0 ? "Red" : "Blue") + " failed " + "to" + " confirm! (" + e.getMessage()
                                + ")");
                matchStatus = (moveCounter % 2 == 0 ? Status.BLUE_WIN : Status.RED_WIN);
                continue;
            }

            // Update move
            try {
                player[(moveCounter + 1) % 2].update(m, board.getStatus());
            } catch (Exception e) {
                System.err.println(
                        ((moveCounter + 1) % 2 == 0 ? "Red" : "Blue") + " " + "failed to update! " + "(" + e.getMessage() + ")");
                matchStatus = ((moveCounter + 1) % 2 == 0 ? Status.BLUE_WIN : Status.RED_WIN);
                continue;
            }

            // Next move
            moveCounter++;

            // Update status
            matchStatus = board.getStatus();
        }

        end(matchStatus);
    }

    /**
     * Game ends with status
     *
     * @param status
     *         Status
     */
    private synchronized void end(Status status) {
        System.out.println("\n\nGame ended with status: " + status);

        notify();
    }

    // ------------------------------------------------------------

    /**
     * Get the match status
     *
     * @return Status
     */
    public Status getStatus() {
        return board.getStatus();
    }
}
