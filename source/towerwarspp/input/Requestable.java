package towerwarspp.input;

import towerwarspp.preset.Move;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public interface Requestable {
    Move request() throws Exception;
}
