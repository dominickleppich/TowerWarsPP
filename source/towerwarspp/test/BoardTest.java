package towerwarspp.test;

import towerwarspp.board.Board;
import towerwarspp.io.TextIO;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.Move;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Status;

import java.util.Random;

/**
 * Created on 12.05.2017.
 *
 * @author dominick
 */
public class BoardTest {
    private static Random rnd;

    static {
        rnd = new Random(System.currentTimeMillis());
    }

    // ------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        startGame(5);
    }

    private static void startGame(int size) throws Exception {
        Board b = new Board(size);
        TextIO textIO = new TextIO();
        textIO.setViewer(b.viewer());

        Player[] players = new Player[2];

        // Init player array
        //players[0] = new HumanPlayer(textIO);
        players[1] = new RandomAI();
        players[1] = new RandomAI();

        // Init players
        players[0].init(size, PlayerColor.RED);
        players[1].init(size, PlayerColor.BLUE);

        // Print board first time
        System.out.println(b);
        int index = 0;

        while (b.getStatus() == Status.OK) {
            //Thread.sleep(50);

            System.out.println("------------------------------------------------------------");
            System.out.println("It's " + b.getTurn() + " turn...");

            Move m = players[index % 2].request();
            System.out.println("[LOG] Move No " + (index + 1) + ": " + m + " " + (b.makeMove(
                    m) ? "succeeded" : "failed"));

            // Confirm and update players
            players[index % 2].confirm(b.getStatus());
            players[(index + 1) % 2].update(m, b.getStatus());

            //System.out.println(b);

            // Next move
            index++;
        }

        System.out.println("\n\nGame ended with status: " + b.getStatus());
    }
}

