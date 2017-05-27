package towerwarspp.input;

import towerwarspp.board.Board;
import towerwarspp.preset.Move;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created on 27.05.2017.
 *
 * @author dominick
 */
public class TextIO implements Requestable {
    private BufferedReader rd;

    // TODO replace by viewer
    // To check if move is valid
    private Board board;

    // ------------------------------------------------------------

    /**
     * Default ctr
     *
     * @param board
     *         Board
     */
    public TextIO(Board board) {
        this.board = board;

        rd = new BufferedReader(new InputStreamReader(System.in));
    }

    // ------------------------------------------------------------

    @Override
    public Move request() throws Exception {
        Move m = null;
        boolean valid = true;
        boolean correctFormat = true;

        do {
            if (!valid) {
                System.out.println("\tThe move you entered was not valid! Please try again...");
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
            } catch (Move.MoveFormatException e) {
                correctFormat = false;

                // If this exception occurs, the move couldn't be checked, thus no valid message needed
                valid = true;
            }

        } while (!correctFormat || !board.checkMove(m));

        return m;
    }
}
