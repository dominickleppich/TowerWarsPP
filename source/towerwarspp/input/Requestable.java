package towerwarspp.input;

import towerwarspp.board.MoveAnalyzer;
import towerwarspp.output.Viewer;
import towerwarspp.preset.Move;

import java.util.Set;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public interface Requestable {
    /**
     * Request a {@link Move} from a player
     *
     * @param possibleMoves
     *         Set of possible moves
     *
     * @return {@link Move}
     *
     * @throws Exception
     *         if something unexpected fails
     */
    Move request(Set<Move> possibleMoves, MoveAnalyzer analyzer) throws Exception;
}
