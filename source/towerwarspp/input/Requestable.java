package towerwarspp.input;

import towerwarspp.output.Viewer;
import towerwarspp.preset.Move;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public interface Requestable {
    /**
     * Request a {@link Move} from a player
     *
     * @param viewer
     *         Board viewer (to validate move)
     *
     * @return {@link Move}
     *
     * @throws Exception
     *         if something unexpected fails
     */
    Move request(Viewer viewer) throws Exception;
}
