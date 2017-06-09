package towerwarspp.test;

import towerwarspp.game.Match;
import towerwarspp.input.Requestable;
import towerwarspp.input.TextIO;
import towerwarspp.player.HumanPlayer;
import towerwarspp.player.ai.RandomAI;
import towerwarspp.preset.ArgumentParser;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerType;

import java.util.Observer;

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
            TextIO t = new TextIO();
            Requestable requestable = t;
            Observer observer = t;

            // Create players
            Player red, blue;
            red = createPlayer(ap.getRed(), requestable);
            blue = createPlayer(ap.getBlue(), requestable);

            // Create match
            Match match = new Match(red, blue, ap.getSize(), observer);
            match.init();

            // Start match
            match.start();
            match.waitMatch();
        } catch (Exception e) {
            System.err.println("Failed to start game: " + e.getMessage());
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
                // TODO implement this
                return null;
        }
        return null;
    }
}
