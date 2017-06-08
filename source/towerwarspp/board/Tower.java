package towerwarspp.board;

import towerwarspp.preset.PlayerColor;

/**
 * Created by dominick on 5/29/17.
 */
public class Tower extends Cell {
    private int height = 1;
    private boolean blocked = false;

    public Tower(PlayerColor color) {
        super(color);
    }

    @Override
    public Cell clone() {
        Tower t = new Tower(getColor());
        t.height = this.height;
        t.blocked = this.blocked;
        return t;
    }

    // ------------------------------------------------------------

    public int getHeight() {
        return height;
    }

    public void increase() {
        height++;
    }

    public void decrease() {
        if (height == 1)
            throw new UnsupportedOperationException("Cannot decrease: height == 1");

        height--;
    }

    // ------------------------------------------------------------

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        if (isBlocked())
            throw new UnsupportedOperationException("Already blocked");

        blocked = true;
    }

    public void unblock() {
        if (!isBlocked())
            throw new UnsupportedOperationException("Not blocked");

        blocked = false;
    }
}
