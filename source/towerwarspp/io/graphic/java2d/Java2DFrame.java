package towerwarspp.io.graphic.java2d;


import towerwarspp.io.InputOutputable;
import towerwarspp.io.Viewer;
import towerwarspp.player.ai.rating.RateStrategy;
import towerwarspp.preset.Move;

import javax.swing.*;

/**
 * Graphical in- and output class using the Java2D framework.
 *
 * @author Dominick Leppich
 */
public class Java2DFrame extends JFrame implements InputOutputable{
    private DrawPanel panel;
    private JLabel statusLabel;

    // ------------------------------------------------------------

    public Java2DFrame() {
        Box vBox = Box.createVerticalBox();
        panel = new DrawPanel();
        vBox.add(panel);

        statusLabel = new JLabel();
        vBox.add(statusLabel);

        add(vBox);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // ------------------------------------------------------------

    private void show(Move move) {
        repaint();

        // Update label
        statusLabel.setText("Move: " + move);
    }

    // ------------------------------------------------------------

    @Override
    public void setViewer(Viewer viewer) {
        panel.setViewer(viewer);
        pack();
        setVisible(true);
        repaint();
    }

    @Override
    public void update(Move move) {
        show(move);
    }

    @Override
    public Move deliver() throws
            Exception {
        return panel.request();
    }
}
