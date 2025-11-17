package racingcar.domain.strategy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import racingcar.domain.Car;

public class StrategyAi {

    private final int simulationCount;

    public StrategyAi(int simulationCount) {
        this.simulationCount = simulationCount;
    }

    public Strategy decideBestStrategy(Car me, List<Car> all, int remainTurns) {
        Strategy best = Strategy.NORMAL;
        double bestScore = -1;

        for (Strategy s : Strategy.values()) {
            double score = simulateManyTimes(me, all, remainTurns, s);
            if (score > bestScore) {
                bestScore = score;
                best = s;
            }
        }
        return best;
    }

    private double simulateManyTimes(Car me, List<Car> all, int remainTurns, Strategy s) {
        int wins = 0;
        for (int i = 0; i < simulationCount; i++) {
            if (simulateOnce(me, all, remainTurns, s)) {
                wins++;
            }
        }
        return (double) wins / simulationCount;
    }

    private boolean simulateOnce(Car me, List<Car> all, int remainTurns, Strategy s) {
        int myPos = me.getPosition();
        int maxOther = all.stream().mapToInt(Car::getPosition).max().orElse(0);

        for (int r = 0; r < remainTurns; r++) {
            myPos += s.move();
            maxOther += ThreadLocalRandom.current().nextInt(2);
        }
        return myPos >= maxOther;
    }

}
