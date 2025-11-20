package racingcar.domain.strategy;

import java.util.concurrent.ThreadLocalRandom;

public enum Strategy {
    AGGRESSIVE(0.6,2),
    NORMAL(0.5,1),
    SAFE(0.8, 1);

    private static final int NO_MOVE = 0;

    private final double successThreshold;
    private final int moveDistance;

    Strategy(double successThreshold, int moveDistance) {
        this.successThreshold = successThreshold;
        this.moveDistance = moveDistance;
    }

    public int move() {
        double r = ThreadLocalRandom.current().nextDouble();
        return decideMove(r);
    }

    int decideMove(double randomValue) {
        if (randomValue < successThreshold) {
            return moveDistance;
        }
        return NO_MOVE;
    }
}
