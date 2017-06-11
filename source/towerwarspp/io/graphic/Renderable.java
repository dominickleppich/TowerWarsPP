package towerwarspp.io.graphic;

import java.awt.*;

/**
 * Renderable objects.
 *
 * @author Dominick Leppich
 */
public interface Renderable {
    /**
     * Render itself with a given {@link Graphics2D} context.
     *
     * @param g
     *         Graphics context
     */
    void render(Graphics2D g);
}
