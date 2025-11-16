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
            myPos += simulateMove(s);

            maxOther += ThreadLocalRandom.current().nextInt(2);
        }

        return myPos >= maxOther;
    }

    public static int simulateMove(Strategy s) {
        double r = ThreadLocalRandom.current().nextDouble();
        return switch (s) {
            case AGGRESSIVE -> r < 0.6 ? 2 : 0;
            case NORMAL -> r < 0.5 ? 1 : 0;
            case SAFE -> r < 0.8 ? 1 : 0;
        };
    }

}
