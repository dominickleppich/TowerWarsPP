package towerwarspp.io.graphic.java2d.render;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by dominick on 6/21/17.
 */
public class Grid extends GComponent {
    private GComponent[][] grid;
    private int xOffset, yOffset;

    // --

    public Grid(int xSize, int ySize, int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
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

    // --

    @Override
    public void draw(Graphics2D g) {
        AffineTransform root = g.getTransform();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                AffineTransform t = new AffineTransform(root);
                t.translate(y * xOffset + x * grid[x][y].getWidth(), x * yOffset + y * grid[x][y].getHeight());
                g.setTransform(t);
                grid[x][y].render(g);
            }
        }
    }
}