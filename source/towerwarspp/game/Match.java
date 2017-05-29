package towerwarspp.game;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import towerwarspp.board.Board;
import towerwarspp.input.TextIO;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.Move;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Status;

import java.util.Observer;

/**
 * This class handles a single match. It requests, confirms and updates moves of both players, initializes them
 * correctly and terminates after the game has finished.
 * The game runs in an own thread. It is possible to wait for the match to terminate.
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
    private Observer observer;

    // ------------------------------------------------------------

    /**
     * Create a new match with given red and blue players and specifying the boards size.
     *
     * @param red
     *         Red player
     * @param blue
     *         Blue player
     * @param size
     *         Board size
     */
    public Match(Player red, Player blue, int size, Observer observer) {
        player = new Player[] {red, blue};
        this.size = size;
        board = new Board(size);
        this.observer = observer;
        if (observer != null)
            board.addObserver(observer);
    }

    // ------------------------------------------------------------

    /**
     * Starts the match.
     *
     * @throws RuntimeException
     *         if the game is already running
     */
    public void start() throws Exception {
        if (matchThread != null)
            throw new RuntimeException("Match is already running!");
        if (!initialized)
            throw new Exception("Match is not initialized!");

        matchThread = new Thread(this);
        matchThread.start();
    }

    /**
     * Stop the game
     */
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
     */
    public void init() throws Exception {
        if (initialized)
            throw new RuntimeException("Match was already initialized!");

        player[0].init(size, PlayerColor.RED);
        player[1].init(size, PlayerColor.BLUE);
        initialized = true;
    }

    /**
     * The main game loop. The match requests moves from both players and confirms and updates them accordingly.
     * Exceptions are handled as lose.
     */
    @Override
    public void run() {
        // Print board first time
        System.out.println(board);
        int moveCounter = 0;

        Status matchStatus = Status.OK;

        while (matchStatus == Status.OK) {
            //Thread.sleep(50);

            System.out.println("------------------------------------------------------------");
            System.out.println("It's " + board.getTurn() + " turn...");

            // TODO
            for (Move m : board.getPossibleMoves())
                System.out.println("\t" + m);

            // Request move
            Move m = null;
            try {
                m = player[moveCounter % 2].request();
            } catch (Exception e) {
                System.out.println((moveCounter % 2 == 0 ? "Red" : "Blue") + " failed to request!");
                matchStatus = (moveCounter % 2 == 0 ? Status.BLUE_WIN : Status.RED_WIN);
                continue;
            }
            System.out.println("LOG Move No " + (moveCounter + 1) + ": " + m + " " + (board.makeMove(m) ?
                                                                                             "succeeded" : "failed"));

            // Confirm move
            try {
                player[moveCounter % 2].confirm(board.getStatus());
            } catch (Exception e) {
                System.out.println((moveCounter % 2 == 0 ? "Red" : "Blue") + " failed to confirm!");
                matchStatus = (moveCounter % 2 == 0 ? Status.BLUE_WIN : Status.RED_WIN);
                continue;
            }

            // Update move
            try {
                player[(moveCounter + 1) % 2].update(m, board.getStatus());
            } catch (Exception e) {
                System.out.println(((moveCounter + 1) % 2 == 0 ? "Red" : "Blue") + " failed to update!");
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

        // Remove observer
        board.deleteObserver(observer);

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
