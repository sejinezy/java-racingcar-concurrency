package racingcar.domain;

import java.util.ArrayList;
import java.util.List;
import racingcar.application.dto.RoundResult;
import racingcar.domain.port.PickRandomValue;

public class RacingTurnRunner {

    private static final int MOVE_THRESHOLD = 4;

    private final ParticipatingCars participatingCars;
    private final PickRandomValue pickRandomValue;

    public RacingTurnRunner(ParticipatingCars participatingCars, PickRandomValue pickRandomValue) {
        this.participatingCars = participatingCars;
        this.pickRandomValue = pickRandomValue;
    }

    public RoundResult runOneTime() {
        for (Car car : participatingCars.getCars()) {
            operate(car);
        }
        return resultOneTime();
    }

    private void operate(Car car) {
        int randomValue = pickRandomValue.pickRandomNumber();

        if (randomValue >= MOVE_THRESHOLD) {
            car.moveForward();
        }
    }

    private RoundResult resultOneTime() {
        List<CarPosition> gameResult = new ArrayList<>();

        for (Car car : participatingCars.getCars()) {
            CarPosition carPosition = new CarPosition(car.getName(), car.getPosition());
            gameResult.add(carPosition);
        }
        return new RoundResult(gameResult);
    }

}
