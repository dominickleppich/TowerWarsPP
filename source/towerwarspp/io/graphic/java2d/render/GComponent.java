package towerwarspp.io.graphic.java2d.render;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by dominick on 6/21/17.
 */
public abstract class GComponent {
    private AffineTransform transform = new AffineTransform();

    // --

    public void setTransform(AffineTransform transform) {
        this.transform = transform;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    // --

    public abstract int getWidth();

    public abstract int getHeight();

    // --

    public void render(Graphics2D g) {
        AffineTransform root = g.getTransform();
        AffineTransform change = new AffineTransform(root);
        change.concatenate(getTransform());
        g.setTransform(change);
        //root.preConcatenate(getTransform());
        draw(g);
        g.setTransform(root);
    }

    public abstract void draw(Graphics2D g);
}
