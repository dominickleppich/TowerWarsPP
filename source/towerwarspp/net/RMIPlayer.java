package towerwarspp.net;

import towerwarspp.Boot;
import towerwarspp.io.Display;
import towerwarspp.player.AbstractPlayer;
import towerwarspp.preset.Move;
import towerwarspp.preset.Player;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Status;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A simple player who can be bound to a rmi registry, simply forwarding all method calls to a given player.
 *
 * @author  Dominick Leppich
 */
public class RMIPlayer extends UnicastRemoteObject implements Player {
    private Player player;
    private Display display;

    // ------------------------------------------------------------

    public RMIPlayer(Player player, Display display) throws RemoteException {
        super();
        this.player = player;
        this.display = display;
    }

    // ------------------------------------------------------------

    @Override
    public Move request() throws Exception {
        Move move = player.request();
        String analyzeResult = null;
        if (Boot.isAnalyze() && player instanceof AbstractPlayer)
            analyzeResult = ((AbstractPlayer) player).getViewer().getMoveAnalyzer().analyzeMove(move);

        display.update(move);

        if (analyzeResult != null)
            System.out.println("Analyze result for move " + move + ": " + analyzeResult);

        if (Boot.isDebug() && player instanceof AbstractPlayer)
            System.out.println(((AbstractPlayer) player).getViewer());

        return move;
    }

    @Override
    public void confirm(Status boardStatus) throws Exception {
        player.confirm(boardStatus);
    }

    @Override
    public void update(Move opponentMove, Status boardStatus) throws Exception {
        String analyzeResult = null;
        if (Boot.isAnalyze() && player instanceof AbstractPlayer)
            analyzeResult = ((AbstractPlayer) player).getViewer().getMoveAnalyzer().analyzeMove(opponentMove);

        player.update(opponentMove, boardStatus);
        display.update(opponentMove);

        if (analyzeResult != null)
            System.out.println("Analyze result for move " + opponentMove + ": " + analyzeResult);

        if (Boot.isDebug() && player instanceof AbstractPlayer)
            System.out.println(((AbstractPlayer) player).getViewer());
    }

    @Override
    public void init(int size, PlayerColor color) throws Exception {
        player.init(size, color);

        // Hacky
        if (player instanceof AbstractPlayer)
            display.setViewer(((AbstractPlayer) player).getViewer());
    }
}
