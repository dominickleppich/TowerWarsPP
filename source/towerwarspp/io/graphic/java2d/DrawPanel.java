package towerwarspp.io.graphic.java2d;

import towerwarspp.io.Viewer;
import towerwarspp.io.graphic.java2d.render.*;
import towerwarspp.preset.Move;
import towerwarspp.preset.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Panel where the drawing magic happens...
 *
 * @author Dominick Leppich
 */
public class DrawPanel extends JPanel {
    private static final Dimension DEFAULT_DIMENSION = new Dimension(1280, 720);
    private static final int BOARD_SIZE = 10;
    private static final int HEX_SIZE = 20;
    private static final Point BOARD1_TRANSLATE = new Point(10, 10);
    private static final Point BOARD2_TRANSLATE = new Point(500, 60);
    private static final double BASE_SIZE = 0.8;
    private static final double TOKEN_SIZE = 0.7;
    private static final int TOWER_RING_DISTANCE = 4;
    private static final float STROKE = 2f;
    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    private static final Font RATING_FONT = new Font(Font.SERIF, Font.ITALIC, 40);

    // ------------------------------------------------------------

    private LinkedList<GComponent> components;
    private boolean enabled;
    private Viewer viewer;
    private Move move;
    private Position tmpPos;
    private HashMap<Position, Polygon> grid;

    private Background background;

    // ------------------------------------------------------------

    public DrawPanel() {
        components = new LinkedList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
            }
        });

        init();
    }

    private synchronized void init() {
        background = new Background(Color.WHITE);
        components.add(background);

        Grid grid1 = new Grid(BOARD_SIZE, BOARD_SIZE, 0, 0);
        AffineTransform gridTransform = new AffineTransform();
        gridTransform.translate(BOARD1_TRANSLATE.getX(), BOARD1_TRANSLATE.getY());
        grid1.setTransform(gridTransform);
        for (int x = 0; x < BOARD_SIZE; x++)
            for (int y = 0; y < BOARD_SIZE; y++)
                grid1.setEntry(x, y, new Hex(HEX_SIZE));
        components.add(grid1);

        HexGrid grid2 = new HexGrid(BOARD_SIZE, BOARD_SIZE);
        AffineTransform grid2Transform = new AffineTransform();
        grid2Transform.translate(BOARD2_TRANSLATE.getX(), BOARD2_TRANSLATE.getY());
        grid2.setTransform(grid2Transform);
        for (int x = 0; x < BOARD_SIZE; x++)
            for (int y = 0; y < BOARD_SIZE; y++)
                grid2.setEntry(x, y, new Hex(HEX_SIZE));
        components.add(grid2);
    }

    // ------------------------------------------------------------

    @Override
    public Dimension getPreferredSize() {
        return DEFAULT_DIMENSION;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    // ------------------------------------------------------------

    public synchronized void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;

        // Antialiasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Render everything
        for (GComponent c : components)
            c.render(g);
    }

    public Move request() {
        return null;
    }
}
