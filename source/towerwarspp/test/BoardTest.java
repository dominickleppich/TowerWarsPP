package towerwarspp.test;

import towerwarspp.board.Board;
import towerwarspp.input.Requestable;
import towerwarspp.input.TextIO;
import towerwarspp.preset.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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

    public static void main(String[] args) throws InterruptedException {
        Board b = new Board(5, 5);
        TextIO input = new TextIO(b);
        b.addObserver(input);

        System.out.println(b);
        int index = 0;
        boolean red = true;

        while (b.getStatus().isOK()) {
            //Thread.sleep(50);

            System.out.println("------------------------------------------------------------");
            System.out.println("It's " + (red ? "reds" : "blues") + " turn...");

            Move m = null;
            try {
                m = input.request();
            } catch (Exception e) {
                m = getRandomMoveFromSet(b.getPossibleMoves());
            }


            System.out.println("Do move No " + ++index + ": " + m + " " + (b.makeMove(m) ? "succeeded" : "failed"));

            //System.out.println(b);

            red = !red;
        }

        System.out.println("\n\nGame ended with status: " + b.getStatus());
    }

    private static Move getRandomMoveFromSet(Set<Move> moves) {
        int i = rnd.nextInt(moves.size()) + 1;

        Move m = null;

        Iterator<Move> it = moves.iterator();
        while (i-- > 0) m = it.next();

        return m;
    }
}
