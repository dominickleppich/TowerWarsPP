package towerwarspp.test;

import towerwarspp.game.Match;
import towerwarspp.io.InputOutputable;
import towerwarspp.io.TextIO;
import towerwarspp.io.graphic.java2d.Java2DFrame;
import towerwarspp.player.HumanPlayer;
import towerwarspp.player.ai.BestRatedMoveAI;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.ArgumentParser;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerType;
import towerwarspp.preset.Requestable;

/**
 * Created on 09.06.2017.
 *
 * @author dominick
 */
public class StartTest {
    public static void main(String[] args) {
        try {
            // Parse arguments
            ArgumentParser ap = new ArgumentParser(args);

            // Create requestable and observer
            InputOutputable inout;
            if (ap.isGraphic())
                inout = new Java2DFrame();
            else
                inout = new TextIO();

            // Create players
            Player red, blue;
            red = createPlayer(ap.getRed(), inout);
            blue = createPlayer(ap.getBlue(), inout);

            // Create match
            Match match = new Match(red, blue, ap.getSize(), inout);
            match.init();

            // Start match
            match.start();
            match.waitMatch();
        } catch (Exception e) {
            System.err.println("Failed to start game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------

    private static Player createPlayer(PlayerType type, Requestable requestable) {
        switch (type) {
            case HUMAN:
                return new HumanPlayer(requestable);
            case RANDOM_AI:
                return new RandomAI();
            case SIMPLE_AI:
                return new BestRatedMoveAI();
        }
        return null;
    }
}
