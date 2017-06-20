package towerwarspp.net;

import towerwarspp.Boot;
import towerwarspp.preset.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the communication with the rmi registry.
 *
 * @author Dominick Leppich
 */
public class RMIHandler {
    private Registry registry;

    // ------------------------------------------------------------

    public RMIHandler() {
        this(1099);
    }

    public RMIHandler(int port) {
        this("localhost", port);
    }

    public RMIHandler(String host) {
        this(host, 1099);
    }

    public RMIHandler(String host, int port) {
        if (Boot.isDebug())
            System.out.println("Creating rmi handler for host " + host + " on port " + port);
        try {
            if (host.equals("localhost"))
                try {
                    registry = LocateRegistry.createRegistry(port);
                } catch (ExportException e) {
                    registry = LocateRegistry.getRegistry(port);
                }
            else
                registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------

    /**
     * Offer a player on the rmi registry.
     *
     * @param p    Player
     * @param name Name
     * @throws RemoteException if connection fails
     */
    public void offer(RMIPlayer p, String name) throws RemoteException {
        registry.rebind(name, p);
    }

    /**
     * Find a player with a given name.
     *
     * @param name Name
     * @return Player
     * @throws RemoteException   if connection fails
     * @throws NotBoundException if no player with this name is bound
     */
    public Player find(String name) throws RemoteException, NotBoundException {
        return (Player) registry.lookup(name);
    }

    /**
     * Find all players.
     *
     * @return Map of all players and names.
     * @throws RemoteException   if connection fails
     * @throws NotBoundException if listing players fails
     */
    public Map<String, Player> list() throws RemoteException, NotBoundException {
        String[] list = registry.list();
        Map<String, Player> map = new HashMap<>();
        for (int i = 0; i < list.length; i++)
            map.put(list[i], find(list[i]));
        return map;
    }
}
