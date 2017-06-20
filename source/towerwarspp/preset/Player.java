package towerwarspp.preset;

import java.rmi.*;

public interface Player extends Remote {
    Move request() throws Exception, RemoteException;

    void confirm(Status boardStatus) throws Exception, RemoteException;

    void update(Move opponentMove, Status boardStatus) throws Exception, RemoteException;

    void init(int size, PlayerColor color) throws Exception, RemoteException;
}
