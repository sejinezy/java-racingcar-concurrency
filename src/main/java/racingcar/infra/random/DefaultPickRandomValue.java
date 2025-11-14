package racingcar.infra.random;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.domain.port.PickRandomValue;

public class DefaultPickRandomValue implements PickRandomValue {

    private static final int RANDOM_MIN_VALUE = 0;
    private static final int RANDOM_MAX_VALUE = 9;

    @Override
    public int pickRandomNumber() {
        return Randoms.pickNumberInRange(RANDOM_MIN_VALUE, RANDOM_MAX_VALUE);
    }

}
