package racingcar.domain.multithread;


import java.util.concurrent.Callable;
import racingcar.domain.Car;
import racingcar.domain.CarPosition;
import racingcar.domain.port.PickRandomValue;
import racingcar.util.CpuWork;

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

        CpuWork.heavyWork(); // 더미 연산

        if (randomValue >= MOVE_THRESHOLD) {
            car.moveForward();
        }
        return new CarPosition(car.getName(), car.getPosition());
    }
}
