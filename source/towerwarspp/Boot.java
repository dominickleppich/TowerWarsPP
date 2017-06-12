package towerwarspp;

import towerwarspp.game.Match;
import towerwarspp.io.InputOutputable;
import towerwarspp.io.TextIO;
import towerwarspp.io.graphic.java2d.Java2DFrame;
import towerwarspp.player.HumanPlayer;
import towerwarspp.player.ai.BestRatedMoveAI;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.*;

/**
 * Start class.
 *
 * @author Dominick Leppich
 */
public class Boot {
    private static int delay;

    // ------------------------------------------------------------

    public static void main(String[] args) {
        try {
            // Parse arguments
            ArgumentParser ap = new ArgumentParser(args);

            if (ap.isSet("delay"))
                delay = ap.getDelay();
            else
                delay = 0;

            // Create requestable and observer
            InputOutputable inout; if (ap.isGraphic())
                inout = new Java2DFrame();
            else
                inout = new TextIO();

            // Create players
            Player red, blue; red = createPlayer(ap.getRed(), inout);
            blue = createPlayer(ap.getBlue(), inout);

            // Create match
            Match match = new Match(red, blue, ap.getSize(), inout);
            match.init();

            // Start match
            match.start(); match.waitMatch();
        } catch (ArgumentParserException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Something went wrong with your parameters... :(\n");
            sb.append("All parameters not written in [] have to be set!\n\n");
            sb.append("The possible parameters are:\n"); sb.append(
                    "\t-red\t\t\tSet the red player type {human, random, " +
                            "simple}\n");
            sb.append(
                    "\t-blue\t\t\tSet the blue player type {human, random, " +
                            "simple}\n");
            sb.append("\t-size\t\t\tSet the board size {4, 5, 6, ..., 26}\n");
            sb.append("\t-delay\t\t\tDelay between moves\n");
            sb.append("\t[--graphic]\t\tActivate a graphical in- and output\n");
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.err.println("Failed to init or start match!");
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------

    /**
     * Get delay.
     *
     * @return delay
     */
    public static int getDelay() {
        return delay;
    }

    // ------------------------------------------------------------

    /**
     * Create a player of a given type.
     *
     * @param type
     *         Player type
     * @param requestable
     *         Requestable
     *
     * @return Player
     */
    private static Player createPlayer(PlayerType type,
                                       Requestable requestable) {
        switch (type) {
            case HUMAN: return new HumanPlayer(requestable);
            case RANDOM_AI: return new RandomAI();
            case SIMPLE_AI: return new BestRatedMoveAI();
        } return null;
    }
}
