package towerwarspp.player.ai.better;

import towerwarspp.board.Board;
import towerwarspp.player.ai.rating.RateStrategy;
import towerwarspp.preset.Move;
import towerwarspp.preset.PlayerColor;
import towerwarspp.preset.Position;
import towerwarspp.preset.Status;

import static towerwarspp.preset.PlayerColor.BLUE;
import static towerwarspp.preset.PlayerColor.RED;

/**
 * Created on 12.06.2017.
 *
 * @author dominick
 */
public class BetterRatingStrategy implements RateStrategy {
    @Override
    public int rate(Board board, Move move) {
        if (move == null)
            return Integer.MIN_VALUE;

        // Simulate a move and rate it
        Board tmpBoard = board.clone();

        PlayerColor ownColor = tmpBoard.getTurn();
        PlayerColor oppColor = ownColor == RED ? BLUE : RED;
        // Count token and tower
        int ownTokenBefore = tmpBoard.getPlayerTokenCount(ownColor);
        int oppTokenBefore = tmpBoard.getPlayerTokenCount(oppColor);
        int ownTowerBefore = tmpBoard.getPlayerTowerCount(ownColor);
        int oppTowerBefore = tmpBoard.getPlayerTowerCount(oppColor);

        tmpBoard.makeMove(move);

        // Count token and tower
        int ownTokenAfter = tmpBoard.getPlayerTokenCount(ownColor);
        int oppTokenAfter = tmpBoard.getPlayerTokenCount(oppColor);
        int ownTowerAfter = tmpBoard.getPlayerTowerCount(ownColor);
        int oppTowerAfter = tmpBoard.getPlayerTowerCount(oppColor);

        Status status = tmpBoard.getStatus();
        if (status == Status.RED_WIN || status == Status.BLUE_WIN)
            return Integer.MAX_VALUE;

        int rating = 0;

        // Distance
        Position baseDestination = ownColor == RED ? tmpBoard.getBlueBasePosition() : tmpBoard.getRedBasePosition();
        int distanceBefore = tmpBoard.distance(move.getStart(), baseDestination);
        int distanceAfter = tmpBoard.distance(move.getEnd(), baseDestination);

        int deltaDistance = distanceBefore - distanceAfter;

        rating += board.getSize() - distanceAfter;

        rating += 1 * deltaDistance;

        // Kicking tokens and towers increase rating
        if (oppTokenAfter < oppTokenBefore)
            rating += 5;

        if (oppTowerAfter < oppTowerBefore)
            rating += 10;

        // Building towers is good as well
//        if (ownTowerAfter > ownTowerBefore)
//            rating += 3;

        return rating;
    }
}
