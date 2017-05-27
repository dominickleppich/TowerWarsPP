package towerwarspp.input;

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
     * @return {@link Move}
     *
     * @throws Exception,
     *         if something unexpected fails
     */
    Move request() throws Exception;
}
