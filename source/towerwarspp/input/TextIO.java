package towerwarspp.input;

import towerwarspp.output.Viewer;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.Move;
import towerwarspp.preset.MoveFormatException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Created on 27.05.2017.
 *
 * @author dominick
 */
public class TextIO implements Requestable, Observer {
    private BufferedReader rd;

    // ------------------------------------------------------------

    /**
     * Default ctr
     */
    public TextIO() {
        rd = new BufferedReader(new InputStreamReader(System.in));
    }

    // ------------------------------------------------------------

    @Override
    public Move request(Set<Move> possibleMoves) throws Exception {
        Move m = null;
        boolean valid = true;
        boolean correctFormat = true;

        do {
            if (!valid) {
                System.out.println("\tThe move you entered was not valid! Please try again...\n\tA possible move is: " +
                                           "" + RandomAI.getRandomMoveFromSet(possibleMoves));
            }
            // TODO IO class
            if (!correctFormat) {
                System.out.println("\tYour move syntax was not correct!");
                System.out.println("\tThe move format is: LetterNumber->LetterNumber");
                System.out.println("\tTo move the token at position A1 to position B2 type:");
                System.out.println("\tA1->B2\t\t(lower case syntax is allowed as well... a1->b2)");
            }
            System.out.print("Please enter a move: ");

            try {
                m = Move.parseMove(rd.readLine());
                correctFormat = true;

                // If this loop runs again, the move was not valid
                valid = false;
            } catch (MoveFormatException e) {
                correctFormat = false;

                // If this exception occurs, the move couldn't be checked, thus no valid message needed
                valid = true;
            }

        } while (!correctFormat || !possibleMoves.contains(m));

        return m;
    }

    // ------------------------------------------------------------

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Move: " + o + "\n" + observable);
    }
}
