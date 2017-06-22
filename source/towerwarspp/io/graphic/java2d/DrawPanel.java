package towerwarspp.io.graphic.java2d;

import towerwarspp.board.Base;
import towerwarspp.board.Cell;
import towerwarspp.board.Token;
import towerwarspp.board.Tower;
import towerwarspp.io.Viewer;
import towerwarspp.io.graphic.java2d.render.Background;
import towerwarspp.io.graphic.java2d.render.GComponent;
import towerwarspp.io.graphic.java2d.render.Hex;
import towerwarspp.io.graphic.java2d.render.HexGrid;
import towerwarspp.preset.Move;
import towerwarspp.preset.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 * Panel where the drawing magic happens...
 *
 * @author Dominick Leppich
 */
public class DrawPanel extends JPanel {
    private static final Dimension DEFAULT_DIMENSION = new Dimension(1280, 720);
    private static final int BOARD_SIZE = 5;
    private static final int HEX_SIZE = 50;
    private static final Point BOARD_TRANSLATE = new Point(10, 10);
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

    private Background background;
    private HexGrid grid;

    // ------------------------------------------------------------

    public DrawPanel() {
        components = new LinkedList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println("Checking");
                for (int x = 0; x < viewer.getSize(); x++)
                    for (int y = 0; y < viewer.getSize(); y++)
                        if (((Hex) grid.getEntry(x, y)).contains(mouseEvent.getX(), mouseEvent.getY()))
                            System.out.println(((Hex) grid.getEntry(x, y)).getPosition());
            }
        });

        init();
    }

    private synchronized void init() {
        background = new Background(Color.WHITE);
        components.add(background);

        grid = new HexGrid();
        AffineTransform gridTransform = new AffineTransform();
        gridTransform.translate(BOARD_TRANSLATE.getX(), BOARD_TRANSLATE.getY());
        grid.setTransform(gridTransform);
        components.add(grid);
    }

    // ------------------------------------------------------------

    @Override
    public Dimension getPreferredSize() {
        return DEFAULT_DIMENSION;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;

        if (viewer != null) {
            grid.init(viewer.getSize(), viewer.getSize());
            for (int x = 0; x < viewer.getSize(); x++)
                for (int y = 0; y < viewer.getSize(); y++)
                    grid.setEntry(x, y, new Hex(HEX_SIZE, new Position(x + 1, y + 1)));
        }

        update();
    }

    // ------------------------------------------------------------

    public synchronized void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;

        // Antialiasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setStroke(new BasicStroke(STROKE));

        // Render everything
        for (GComponent c : components)
            c.render(g);
    }

    public Move request() {
        return null;
    }

    public void update() {
        if (viewer != null) {
            for (int x = 0; x < viewer.getSize(); x++) {
                for (int y = 0; y < viewer.getSize(); y++) {
                    Hex h = (Hex) grid.getEntry(x, y);
                    Cell c = viewer.getCell(new Position(x + 1, y + 1));
                    if (c instanceof Base)
                        continue;
                    else if (c instanceof Token) {
                        h.setColor(c.getColor());
                        h.setHeight(1);
                        h.setBlocked(false);
                    }
                    else if (c instanceof Tower) {
                        h.setColor(c.getColor());
                        h.setHeight(((Tower) c).getHeight());
                        h.setBlocked(((Tower) c).isBlocked());
                    }
                }
            }
        }
    }
}
