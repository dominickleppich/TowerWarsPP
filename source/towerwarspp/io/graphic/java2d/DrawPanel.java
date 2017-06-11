package towerwarspp.io.graphic.java2d;

import towerwarspp.board.Base;
import towerwarspp.board.Cell;
import towerwarspp.board.Token;
import towerwarspp.board.Tower;
import towerwarspp.io.Viewer;
import towerwarspp.preset.Move;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Panel to draw game on.
 *
 * @author Dominick Leppich
 */
public class DrawPanel extends JPanel implements MouseListener {
    private static final int HEX_SIZE = 50;
    private static final double BASE_SIZE = 0.8;
    private static final int TOKEN_SIZE = 30;
    private static final int TOWER_RING_DISTANCE = 5;

    // ------------------------------------------------------------

    private Viewer viewer;
    private Move move;
    private Position tmpPos;

    private String debugText;

    private HashMap<Position, Polygon> grid;

    // ------------------------------------------------------------

    public DrawPanel() {
        addMouseListener(this);
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;

        if (viewer != null) {
            setPreferredSize(new Dimension(HEX_SIZE * viewer.getSize() * 3,
                                                  HEX_SIZE * viewer.getSize()
                                                          * 2));

            grid = new HashMap<>(); double sizesqrt3 = HEX_SIZE * Math.sqrt(3);
            // Calculate each polygon
            for (int l = 1; l <= viewer.getSize(); l++) {
                for (int n = 1; n <= viewer.getSize(); n++) {
                    int x = (int) (sizesqrt3 * (l + n / 2.0));
                    int y = (int) (HEX_SIZE * 3.0 / 2 * n);

                    grid.put(new Position(l, n), hex(x, y, HEX_SIZE));
                }
            }
        }
    }

    // ------------------------------------------------------------

    @Override
    public void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;

        // Nice graphics
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(2));

        // If no viewer is available, nothing to do
        if (viewer == null)
            return;

        g.setColor(Color.WHITE); g.fillRect(0, 0, (int) getSize().getWidth(),
                (int) getSize().getHeight());

        double sizesqrt3 = HEX_SIZE * Math.sqrt(3);
        for (int l = 1; l <= viewer.getSize(); l++) {
            for (int n = 1; n <= viewer.getSize(); n++) {
                int x = (int) (sizesqrt3 * (l + n  / 2.0));
                int y = (int) (HEX_SIZE * 3.0 / 2 * n);

//                x += 2 * HEX_SIZE; y += 2 * HEX_SIZE;

                Position position = new Position(l, n);
                Cell cell = viewer.getCell(position);

                if (cell != null) {
                    g.setColor(
                            cell.getColor() == PlayerColor.RED ? Color.RED :
                                    Color.BLUE);


                    // Base
                    if (cell instanceof Base)
                        g.fill(hex(x, y, (int) (HEX_SIZE * BASE_SIZE)));
                    else if (cell instanceof Token)
                        g.fill(new Ellipse2D.Float(x - TOKEN_SIZE / 2.0f,
                                                          y - TOKEN_SIZE / 2.0f,
                                                          TOKEN_SIZE,
                                                          TOKEN_SIZE));
                    else {
                        // Blocked towers are dark gray
                        if (((Tower) cell).isBlocked())
                            g.setColor(Color.DARK_GRAY);

                        g.fill(new Ellipse2D.Float(x - TOKEN_SIZE / 2.0f,
                                                          y - TOKEN_SIZE / 2.0f,
                                                          TOKEN_SIZE,
                                                          TOKEN_SIZE));

                        g.setColor(
                                cell.getColor() == PlayerColor.RED ? Color.RED :
                                        Color.BLUE);
                        for (int i = 1; i <= ((Tower) cell).getHeight(); i++)
                            g.draw(new Ellipse2D.Float(x - TOKEN_SIZE / 2.0f
                                                               - i * TOWER_RING_DISTANCE,
                                                              y - TOKEN_SIZE
                                                                          /
                                                                          2.0f - i * TOWER_RING_DISTANCE,
                                                              TOKEN_SIZE + 2 * i * TOWER_RING_DISTANCE,
                                                              TOKEN_SIZE + 2 * i * TOWER_RING_DISTANCE));
                    }

                    g.setColor(Color.WHITE);
                    g.drawString(position.toString(), x - 6, y + 3);
                }


            }
        }

        g.setColor(Color.BLACK); for (Polygon p : grid.values())
            g.draw(p);

        if (debugText != null) {
            g.setColor(Color.BLACK); g.drawString(debugText, 20, 20);
        }
    }

    private int[] hexCorner(int xCenter, int yCenter, int size, int i) {
        int deg = 60 * i + 30; double rad = Math.PI / 180 * deg;
        return new int[] {(int) (xCenter + size * Math.cos(
                rad)), (int) (yCenter + size * Math.sin(rad))};
    }

    private Polygon hex(int xCenter, int yCenter, int size) {
        int[][] xy = new int[6][]; for (int i = 0; i < 6; i++)
            xy[i] = hexCorner(xCenter, yCenter, size, i);

        return new Polygon(new int[] {xy[0][0], xy[1][0], xy[2][0], xy[3][0],
                xy[4][0], xy[5][0]},
                                  new int[] {xy[0][1], xy[1][1], xy[2][1],
                                          xy[3][1], xy[4][1], xy[5][1]},
                                  6);
    }

    private Position pixelToPosition(int x, int y) throws
            IllegalArgumentException {
        x -= 2 * HEX_SIZE; y -= 2 * HEX_SIZE;
        int letter = (int) ((x * Math.sqrt(3) / 3 - y / 3) / HEX_SIZE);
        int number = (int) (y * 2.0 / 3 / HEX_SIZE);
        return new Position(letter + 1, number + 1);
    }

    // ------------------------------------------------------------

    public synchronized Move request() {
        try {
            move = null; tmpPos = null; wait(); return move;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } return null;
    }

    public synchronized void moveReady() {
        notify();
    }

    // ------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint(); Position pos = null;
        for (Map.Entry<Position, Polygon> e : grid.entrySet()) {
            if (e.getValue().contains(p)) {
                pos = e.getKey(); break;
            }
        } debugText = "Clicked " + p + (pos != null ? pos : ""); repaint();

        if (tmpPos == null)
            tmpPos = pos;
        else {
            move = new Move(tmpPos, pos); tmpPos = null; moveReady();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
