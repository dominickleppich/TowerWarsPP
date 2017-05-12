package towerwarspp.test;

import towerwarspp.board.Board;
import towerwarspp.preset.Move;

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
        Board b = new Board(10, 3);

        System.out.println(b);
        int index = 0;
        boolean red = true;

        while (b.getStatus().isOK()) {
            Thread.sleep(50);

            System.out.println("------------------------------------------------------------");

            Move m = getRandomMoveFromSet(b.getPossibleMoves());

            System.out.println("It's " + (red ? "reds" : "blues") + " turn...");
            System.out.println("Do move No " + ++index + ": " + m + " " + (b.makeMove(m) ? "succeeded" : "failed"));

            System.out.println(b);

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
