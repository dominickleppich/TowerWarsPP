package towerwarspp.io;

import towerwarspp.board.MoveAnalyzer;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.Move;
import towerwarspp.preset.MoveFormatException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * Created on 27.05.2017.
 *
 * @author dominick
 */
public class TextIO implements InputOutputable {
    private BufferedReader rd;
    private Viewer viewer;

    // ------------------------------------------------------------

    /**
     * Default ctr
     */
    public TextIO() {
        rd = new BufferedReader(new InputStreamReader(System.in));
    }

    // ------------------------------------------------------------

    @Override
    public Move deliver() throws Exception {
        MoveAnalyzer analyzer = viewer.getMoveAnalyzer();
        Set<Move> possibleMoves = viewer.getPossibleMoves();
        Move m = null;
        boolean valid = true;
        boolean correctFormat = true;

        System.out.println("------------------------------------------------------------");
        System.out.println("It's " + viewer.getTurn() + "'s turn...");
        System.out.println(viewer);

        do {
            if (!valid) {
                System.out.println("\t" + analyzer.analyzeMove(
                        m) + "\n\tPlease try again...\n\tA possible move is: " + "" + "" + "" +
                                           RandomAI.getRandomMoveFromSet(
                        possibleMoves));
            }
            // TODO IO class
            if (!correctFormat) {
                System.out.println("\tYour move syntax was not correct!");
                System.out.println("\tThe move format is: LetterNumber->LetterNumber");
                System.out.println("\tTo move the token at position A1 to position B2 " + "type:");
                System.out.println(
                        "\tA1->B2\t\t(lower case syntax is allowed as " + "well..." + " a1->b2)");
            }
            System.out.print("Please enter a move: ");

            try {
                String line = rd.readLine();
                if (line.equals("surrender"))
                    m = null;
                else
                    m = Move.parseMove(line);
                correctFormat = true;

                // If this loop runs again, the move was not valid
                valid = false;
            } catch (MoveFormatException e) {
                correctFormat = false;

                // If this exception occurs, the move couldn't be checked,
                // thus no valid message needed
                valid = true;
            }

        } while (!correctFormat || !possibleMoves.contains(m));

        return m;
    }

    @Override
    public void update(Move move) {
        System.out.println("Made move: " + move);
        String analyzeResult = viewer.getMoveAnalyzer().analyzeMove(move);
        System.out.println("Analyzing result: " + analyzeResult);
    }

    @Override
    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }
}
