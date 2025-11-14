package racingcar.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class ParticipatingCars {

    private static final String ERR_CARS_DUPLICATE_CAR_NAME = "동일한 이름의 자동차는 같은 게임에 참여할 수 없습니다.";
    private static final String ERR_CARS_SIZE = "두 대 이상의 자동차가 참여해야 합니다.";
    private static final int MIN_CARS_SIZE = 2;

    private final List<Car> cars = new ArrayList<>();

    public ParticipatingCars(List<String> cars) {
        validateUnique(cars);
        validateSize(cars);
        for (String car : cars) {
            this.cars.add(new Car(car));
        }
    }

    private void validateUnique(List<String> cars) {
        if (cars.size() != new HashSet<>(cars).size()) {
            throw new IllegalArgumentException(ERR_CARS_DUPLICATE_CAR_NAME);
        }
    }

    private void validateSize(List<String> cars) {
        if (cars.size() < MIN_CARS_SIZE) {
            throw new IllegalArgumentException(ERR_CARS_SIZE);
        }
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

}
