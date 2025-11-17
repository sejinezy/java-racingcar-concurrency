package racingcar.application.turn;

import java.util.ArrayList;
import java.util.List;
import racingcar.domain.RoundResult;
import racingcar.domain.Car;
import racingcar.domain.CarPosition;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.Strategy;
import racingcar.domain.strategy.StrategyAi;

public class SingleThreadTurnRunner implements TurnRunner {

    private final ParticipatingCars cars;
    private final StrategyAi ai;

    public SingleThreadTurnRunner(ParticipatingCars cars, StrategyAi ai) {
        this.cars = cars;
        this.ai = ai;
    }

    @Override
    public RoundResult run(int remainTurns) {
        for (Car car : cars.getCars()) {
            operate(car,remainTurns);
        }
        return resultOneTime();
    }

    private void operate(Car car,int remainTurns) {
        Strategy strategy = ai.decideBestStrategy(car, cars.getCars(), remainTurns);
        car.moveAccordingTo(strategy);
    }

    private RoundResult resultOneTime() {
        List<CarPosition> gameResult = new ArrayList<>();

        for (Car car : cars.getCars()) {
            CarPosition carPosition = new CarPosition(car.getName(), car.getPosition());
            gameResult.add(carPosition);
        }
        return new RoundResult(gameResult);
    }

}
