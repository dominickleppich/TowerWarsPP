package towerwarspp.io.graphic.java2d;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import towerwarspp.board.Board;
import towerwarspp.board.MoveAnalyzer;
import towerwarspp.io.InputOutputable;
import towerwarspp.io.Viewer;
import towerwarspp.player.ai.RateStrategy;
import towerwarspp.preset.Move;

import javax.swing.*;
import java.util.Observable;
import java.util.Set;

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

    public void setRatingStrategy(RateStrategy strategy) {
        panel.setRatingStrategy(strategy);
    }

    // ------------------------------------------------------------

    private void show(Viewer viewer, Move move) {
        panel.setViewer(viewer);
        pack();
        repaint();

        // Update label
        statusLabel.setText("Move: " + move);
        setVisible(true);
    }

    // ------------------------------------------------------------

    @Override
    public void update(Observable observable, Object o) {
        // Get viewer
        Viewer viewer = ((Board) observable).viewer();
        // Get Move
        Move move;
        if (o == null)
            move = null;
        else
            move = (Move) o;

        show(viewer, move);
    }

    @Override
    public Move request(Set<Move> possibleMoves, MoveAnalyzer analyzer) throws
            Exception {
        return panel.request();
    }
}
