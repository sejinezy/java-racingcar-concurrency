package racingcar.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import racingcar.domain.CarPosition;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.RacingTurnRunner;
import racingcar.application.dto.RoundResult;
import racingcar.domain.port.PickRandomValue;

class RacingGameTest {

    private static class AlwaysMovePicker implements PickRandomValue {
        @Override
        public int pickRandomNumber() {
            return 4;
        }
    }

    private static class NeverMovePicker implements PickRandomValue {
        @Override
        public int pickRandomNumber() {
            return 3;
        }
    }

    @Test
    void 랜덤값이_4_이상이면_전진한다() {
        ParticipatingCars participatingCars = new ParticipatingCars(List.of("pobi", "woni"));
        PickRandomValue value = new AlwaysMovePicker();
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars, value);

        RoundResult oneTimeResult = racingGame.runOneTime();
        assertThat(oneTimeResult.positions()).containsExactly(new CarPosition("pobi", 1), new CarPosition("woni", 1));
    }

    @Test
    void 랜덤값이_4_미만이면_전진하지_않는다() {
        ParticipatingCars participatingCars = new ParticipatingCars(List.of("pobi", "woni"));
        PickRandomValue value = new NeverMovePicker();
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars, value);

        RoundResult oneTimeResult = racingGame.runOneTime();

        assertThat(oneTimeResult.positions()).containsExactly(new CarPosition("pobi", 0), new CarPosition("woni", 0));
    }

    @Test
    void 각각의_결과지는_다음턴에_영향받지_않는다() {
        ParticipatingCars participatingCars = new ParticipatingCars(List.of("pobi", "woni"));
        PickRandomValue value = new AlwaysMovePicker();
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars, value);

        RoundResult oneTimeResult = racingGame.runOneTime();
        RoundResult twoTimeResult = racingGame.runOneTime();

        assertThat(oneTimeResult.positions()).containsExactly(new CarPosition("pobi", 1), new CarPosition("woni", 1));
        assertThat(twoTimeResult.positions()).containsExactly(new CarPosition("pobi", 2), new CarPosition("woni", 2));
    }

    @Test
    void 결과지는_참가하는_자동차의_순서를_보장한다() {
        ParticipatingCars participatingCars = new ParticipatingCars(List.of("pobi", "woni"));
        PickRandomValue value = new AlwaysMovePicker();
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars, value);

        RoundResult oneTimeResult = racingGame.runOneTime();

        assertThat(oneTimeResult.positions()).containsExactly(new CarPosition("pobi", 1), new CarPosition("woni", 1));

    }

}