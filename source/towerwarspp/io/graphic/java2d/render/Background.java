package towerwarspp.io.graphic.java2d.render;

import java.awt.*;

/**
 * Created by dominick on 6/21/17.
 */
public class Background extends GComponent {
    private Color color;

    // --

    public Background() {
        this(Color.BLACK);
    }

    public Background(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    // --

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(g.getClip());
    }
}
