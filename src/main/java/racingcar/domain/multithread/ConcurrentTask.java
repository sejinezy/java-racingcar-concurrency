package racingcar.domain.multithread;


import java.util.concurrent.Callable;
import racingcar.domain.Car;
import racingcar.domain.CarPosition;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.Strategy;
import racingcar.domain.strategy.StrategyAi;

public class ConcurrentTask implements Callable<CarPosition> {

    private final Car car;
    private final StrategyAi ai;
    private final ParticipatingCars cars;
    private final int remainTurns;

    public ConcurrentTask(Car car, StrategyAi ai, ParticipatingCars cars, int remainTurns) {
        this.car = car;
        this.ai = ai;
        this.cars = cars;
        this.remainTurns = remainTurns;
    }


    @Override
    public CarPosition call() {
        Strategy strategy = ai.decideBestStrategy(car, cars.getCars(), remainTurns);
        car.moveAccordingTo(strategy);
        return new CarPosition(car.getName(), car.getPosition());
    }
}
