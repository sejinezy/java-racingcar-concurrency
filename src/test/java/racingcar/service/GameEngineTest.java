package racingcar.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import racingcar.domain.Attempts;
import racingcar.domain.CarPosition;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.RacingTurnRunner;
import racingcar.application.dto.RoundResult;
import racingcar.domain.port.PickRandomValue;

class GameEngineTest {

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
    void 시도_횟수만큼_라운드를_반복한다() {
        Attempts attempts = new Attempts("2");
        ParticipatingCars participatingCars = new ParticipatingCars(List.of("pobi", "woni"));
        PickRandomValue value = new AlwaysMovePicker();
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars, value);

        GameEngine gameEngine = new GameEngine();
        List<RoundResult> gameResults = gameEngine.runAll(attempts, racingGame);

        assertThat(gameResults.size()).isEqualTo(attempts.getNumber());
    }

    @Test
    void 각각의_라운드의_결과를_보관한다() {
        Attempts attempts = new Attempts("2");
        ParticipatingCars participatingCars = new ParticipatingCars(List.of("pobi", "woni"));
        PickRandomValue value = new AlwaysMovePicker();
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars, value);

        GameEngine gameEngine = new GameEngine();
        List<RoundResult> gameResults = gameEngine.runAll(attempts, racingGame);

        RoundResult firstTimeResult = gameResults.getFirst();
        RoundResult secondTimeResult = gameResults.get(1);

        assertThat(firstTimeResult.positions()).containsExactly(
                new CarPosition("pobi", 1), new CarPosition("woni", 1));

        assertThat(secondTimeResult.positions()).containsExactly(
                new CarPosition("pobi", 2), new CarPosition("woni", 2));

    }

    @Test
    void 한명의_우승자를_반환한다() {
        GameEngine gameEngine = new GameEngine();

        RoundResult lastResult = new RoundResult(List.of(new CarPosition("pobi", 2), new CarPosition("woni", 1)));
        List<String> winner = gameEngine.getWinner(lastResult);

        assertThat(winner.size()).isEqualTo(1);
        assertThat(winner).containsExactly("pobi");
    }

    @Test
    void 공동_우승자를_반환한다() {
        GameEngine gameEngine = new GameEngine();

        RoundResult lastResult = new RoundResult(List.of(new CarPosition("pobi", 2), new CarPosition("woni", 2)));
        List<String> winner = gameEngine.getWinner(lastResult);

        assertThat(winner.size()).isEqualTo(2);
        assertThat(winner).containsExactly("pobi", "woni");
    }
}