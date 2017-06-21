package towerwarspp.io.graphic.java2d.render;

import towerwarspp.preset.Position;

import java.awt.*;
import java.util.Random;

/**
 * Created by dominick on 6/21/17.
 */
public class Hex extends GComponent {
    private static Random rnd = new Random(System.currentTimeMillis());
    private Color color;
    private int size;

    // --

    public Hex(int size) {
        this.size = size;
        color = new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }

    // --

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
        Shape s = hex(getWidth() / 2, getHeight() / 2, size);
        g.setColor(color);
        g.fill(s);

        g.setColor(Color.BLACK);
        g.draw(s);
    }

    // --

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
