package racingcar.domain;

import racingcar.domain.strategy.Strategy;

public class Car {

    private static final String ERR_CAR_NAME_TOO_LONG = "이름은 5자 이하만 가능합니다.";
    private static final int MAX_CAR_NAME_LENGTH = 5;

    private final String name;
    private int position;

    public Car(String name) {
        validateLength(name);
        this.name = name;
    }

    public void moveAccordingTo(Strategy strategy) {
        this.position += strategy.move();
    }

    private void validateLength(String name) {
        if (name.length() > MAX_CAR_NAME_LENGTH) {
            throw new IllegalArgumentException(ERR_CAR_NAME_TOO_LONG);
        }
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

}
