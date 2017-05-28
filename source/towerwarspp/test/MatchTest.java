package towerwarspp.test;

import towerwarspp.game.Match;
import towerwarspp.input.TextIO;
import towerwarspp.player.HumanPlayer;
import towerwarspp.player.ai.RandomAI;

/**
 * Created on 28.05.2017.
 *
 * @author dominick
 */
public class MatchTest {
    public static void main(String[] args) throws Exception {
        Match match = new Match(new RandomAI(), new RandomAI(), 5);
        match.init();
        match.start();
        match.waitMatch();
        System.out.println("Hello World");
    }
}
