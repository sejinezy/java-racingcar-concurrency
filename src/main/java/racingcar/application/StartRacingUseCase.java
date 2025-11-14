package racingcar.application;

import java.util.List;
import racingcar.application.dto.RoundResult;
import racingcar.domain.Attempts;
import racingcar.domain.ParticipatingCars;
import racingcar.service.GameEngine;
import racingcar.domain.port.PickRandomValue;
import racingcar.domain.RacingTurnRunner;

public class StartRacingUseCase {

    private final PickRandomValue pickRandomValue;
    private final GameEngine gameEngine;

    public StartRacingUseCase(PickRandomValue pickRandomValue, GameEngine gameEngine) {
        this.pickRandomValue = pickRandomValue;
        this.gameEngine = gameEngine;
    }

    public List<RoundResult> execute(List<String> carNames, String attemptsInput) {
        ParticipatingCars participatingCars = new ParticipatingCars(carNames);
        RacingTurnRunner racingGame = new RacingTurnRunner(participatingCars,pickRandomValue);
        Attempts attempts = new Attempts(attemptsInput);

        return gameEngine.runAll(attempts, racingGame);
    }

    public List<String> extractWinners(List<RoundResult> allResult) {
        return gameEngine.getWinner(allResult.getLast());

    }
}
