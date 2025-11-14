package racingcar.application;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import racingcar.application.dto.RoundResult;
import racingcar.domain.Attempts;
import racingcar.domain.ConcurrentTurnRunner;
import racingcar.domain.ParticipatingCars;
import racingcar.service.GameEngine;
import racingcar.domain.port.PickRandomValue;

public class StartRacingUseCase {

    private final PickRandomValue pickRandomValue;
    private final GameEngine gameEngine;

    public StartRacingUseCase(PickRandomValue pickRandomValue, GameEngine gameEngine) {
        this.pickRandomValue = pickRandomValue;
        this.gameEngine = gameEngine;
    }

    public List<RoundResult> execute(List<String> carNames, String attemptsInput)
            throws InterruptedException, ExecutionException {
        ParticipatingCars participatingCars = new ParticipatingCars(carNames);

        ExecutorService es = Executors.newFixedThreadPool(4);

        ConcurrentTurnRunner concurrentTurnRunner = new ConcurrentTurnRunner(pickRandomValue, participatingCars, es);

        Attempts attempts = new Attempts(attemptsInput);

        return gameEngine.runAll(attempts, concurrentTurnRunner);
    }

    public List<String> extractWinners(List<RoundResult> allResult) {
        return gameEngine.getWinner(allResult.getLast());

    }
}
