package towerwarspp.io.graphic.java2d.render;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by dominick on 6/21/17.
 */
public class HexGrid extends GComponent {
    private GComponent[][] grid;

    // --

    public HexGrid() {    }

    public void init(int xSize, int ySize) {
        grid = new GComponent[xSize][ySize];
    }

    // --

    @Override
    public int getWidth() {
        int width = 0;
        for (int x = 0; x < grid.length; x++)
            width += grid[x][0].getWidth();
        return width;
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (int y = 0; y < grid[0].length; y++)
            height += grid[0][y].getHeight();
        return height;
    }

    // --

    public void setEntry(int x, int y, GComponent c) {
        grid[x][y] = c;
    }

    public GComponent getEntry(int x, int y) {
        return grid[x][y];
    }

    // --

    @Override
    public void draw(Graphics2D g) {
        if (grid == null)
            return;

        AffineTransform root = g.getTransform();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                AffineTransform t = new AffineTransform(root);
                GComponent gc = grid[x][y];
                t.translate(x * gc.getWidth() + y * gc.getWidth() / 2, y * gc.getHeight() * 3 / 4);
                g.setTransform(t);
                grid[x][y].render(g);
            }
        }
    }
}