package towerwarspp.io.graphic.java2d.render;

import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Position;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Created by dominick on 6/21/17.
 */
public class Hex extends GComponent {
    private static Random rnd = new Random(System.currentTimeMillis());
    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    private Position position;
    private int size;
    private Shape hexShape;

    private PlayerColor pColor;
    private boolean blocked;
    private int height;

    // ------------------------------------------------------------

    public Hex(int size, Position position) {
        this.size = size;
        this.position = position;

        //color = new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

        hexShape = hex(getWidth() / 2, getHeight() / 2, size);
        this.pColor = PlayerColor.RED;
        this.height = 0;
        this.blocked = false;
    }

    // ------------------------------------------------------------

    public void setColor(PlayerColor color) {
        this.pColor = color;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Position getPosition() {
        return position;
    }

    // ------------------------------------------------------------

    @Override
    public int getWidth() {
        return (int) (Math.sqrt(3)/2 * getHeight());
    }

    @Override
    public int getHeight() {
        return size * 2;
    }

    @Override
    public void draw(Graphics2D g) {
//        g.setColor(color);
//        g.fill(hexShape);

        g.setColor(Color.BLACK);
        g.draw(hexShape);

//        g.setColor(inverseColor(color));
//        g.drawString("" + g.getTransform().getTranslateX() + ", " + g.getTransform().getTranslateY(), 0, 0);

        if (height > 0) {
            g.setColor(pColor == PlayerColor.RED ? Color.RED : Color.BLUE);
            g.fill(new Ellipse2D.Double(getWidth() / 4, getHeight() / 2 - getWidth() / 4, getWidth() / 2, getWidth()
                                                                                                                  / 2));
        }

        if (position != null) {
            g.setColor(Color.WHITE);
            g.setFont(LABEL_FONT);
            int w = g.getFontMetrics().stringWidth(position.toString());
            int h = (int) g.getFontMetrics().getStringBounds(position.toString(), g).getHeight();
            g.drawString(position.toString(), getWidth() / 2 - w / 2, getHeight() / 2 + h / 2);
        }
    }

    public boolean contains(double x, double y) {
        System.out.println("Checking contains " + position + ": " + x + ", " + y);
        x -= getTransform().getTranslateX();
        x -= getTransform().getTranslateY();
        return hexShape.contains(x, y);
    }

    // ------------------------------------------------------------

    private static Color inverseColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    private static int[] hexCorner(int xCenter, int yCenter, int size, int i) {
        int deg = 60 * i + 30;
        double rad = Math.PI / 180 * deg;
        return new int[] {(int) (xCenter + size * Math.cos(rad)), (int) (yCenter + size * Math.sin(rad))};
    }

    private static Polygon hex(int xCenter, int yCenter, int size) {
        int[][] xy = new int[6][];
        for (int i = 0; i < 6; i++)
            xy[i] = hexCorner(xCenter, yCenter, size, i);

        return new Polygon(new int[] {xy[0][0], xy[1][0], xy[2][0], xy[3][0], xy[4][0], xy[5][0]},
                new int[] {xy[0][1], xy[1][1], xy[2][1], xy[3][1], xy[4][1], xy[5][1]}, 6);
    }

    private static Position pixelToPosition(int x, int y, int size) throws IllegalArgumentException {
        x -= 2 * size;
        y -= 2 * size;
        int letter = (int) ((x * Math.sqrt(3) / 3 - y / 3) / size);
        int number = (int) (y * 2.0 / 3 / size);
        return new Position(letter + 1, number + 1);
    }
}
