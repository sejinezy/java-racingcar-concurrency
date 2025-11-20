package racingcar.domain.strategy;

import java.util.List;
import racingcar.domain.Car;

public class StrategyAi {

    private final WinRateCalculator winRateCalculator;

    public StrategyAi(WinRateCalculator winRateCalculator) {
        this.winRateCalculator = winRateCalculator;
    }

    public Strategy decideBestStrategy(Car me, List<Car> all, int remainTurns) {
        Strategy best = Strategy.NORMAL;
        double bestScore = -1;

        for (Strategy s : Strategy.values()) {
            double score = winRateCalculator.calculate(me, all, remainTurns, s);
            if (score > bestScore) {
                bestScore = score;
                best = s;
            }
        }
        return best;
    }

}
