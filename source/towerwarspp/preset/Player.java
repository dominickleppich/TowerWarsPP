package towerwarspp.preset;

public interface Player {
    Move request() throws Exception;

    void confirm(Status boardStatus) throws Exception;

    void update(Move opponentMove, Status boardStatus) throws Exception;

    void init(int size, PlayerColor color) throws Exception;
}
