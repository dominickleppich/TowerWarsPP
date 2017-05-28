package towerwarspp.preset;

import java.rmi.RemoteException;

/**
 * Created on 05.05.2017.
 *
 * @author dominick
 */
public interface Player {
    Move request() throws Exception;
    void confirm(Status boardStatus) throws Exception;
    void update(Move opponentMove, Status boardStatus) throws Exception;
    void init(int size, PlayerColor color) throws Exception;
}
