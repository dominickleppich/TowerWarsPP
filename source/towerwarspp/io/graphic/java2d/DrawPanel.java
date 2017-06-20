package towerwarspp.io.graphic.java2d;

import towerwarspp.io.Viewer;
import towerwarspp.preset.Move;
import towerwarspp.preset.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Panel where the drawing magic happens...
 *
 * @author Dominick Leppich
 */
public class DrawPanel extends JPanel {
    private static final Dimension DEFAULT_DIMENSION = new Dimension(1280, 720);
    private static final double BASE_SIZE = 0.8;
    private static final double TOKEN_SIZE = 0.7;
    private static final int TOWER_RING_DISTANCE = 4;
    private static final float STROKE = 2f;
    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    private static final Font RATING_FONT = new Font(Font.SERIF, Font.ITALIC, 40);

    // ------------------------------------------------------------

    private boolean enabled;
    private Viewer viewer;
    private Move move;
    private Position tmpPos;
    private HashMap<Position, Polygon> grid;

    // ------------------------------------------------------------

    public DrawPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
            }
        });
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

    public Move request() {
        return null;
    }
}
