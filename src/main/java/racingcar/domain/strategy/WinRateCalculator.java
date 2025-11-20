package racingcar.domain.strategy;

import java.util.List;
import racingcar.domain.Car;

public interface WinRateCalculator {

    double calculate(Car me, List<Car> all, int remainTurns, Strategy s);
}
