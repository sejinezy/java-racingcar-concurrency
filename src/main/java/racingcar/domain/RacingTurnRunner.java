package racingcar.domain;


import java.util.concurrent.Callable;
import racingcar.domain.port.PickRandomValue;

public class RacingTurnRunner implements Callable<CarPosition> {

    private static final int MOVE_THRESHOLD = 4;

    private final Car car;
    private final PickRandomValue pickRandomValue;


    public RacingTurnRunner(Car car,PickRandomValue pickRandomValue) {
        this.car = car;
        this.pickRandomValue = pickRandomValue;
    }


    @Override
    public CarPosition call() {
        int randomValue = pickRandomValue.pickRandomNumber();

        if (randomValue >= MOVE_THRESHOLD) {
            car.moveForward();
        }
        return new CarPosition(car.getName(), car.getPosition());
    }
}
