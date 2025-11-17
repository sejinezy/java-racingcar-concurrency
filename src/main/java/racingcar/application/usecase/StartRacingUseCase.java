package racingcar.application.usecase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import racingcar.application.engine.GameEngine;
import racingcar.domain.RoundResult;
import racingcar.domain.Attempts;
import racingcar.application.turn.TurnRunner;
import racingcar.application.turn.ConcurrentTurnRunner;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.StrategyAi;

public class StartRacingUseCase {

    private final static int THREADS = 8;

    private final StrategyAi ai;
    private final GameEngine gameEngine;

    public StartRacingUseCase(StrategyAi ai, GameEngine gameEngine) {
        this.ai = ai;
        this.gameEngine = gameEngine;
    }

    public List<RoundResult> execute(List<String> carNames, String attemptsInput)
            throws InterruptedException, ExecutionException {

        ParticipatingCars participatingCars = new ParticipatingCars(carNames);
        ExecutorService es = Executors.newFixedThreadPool(THREADS);
        TurnRunner concurrentTurnRunner = new ConcurrentTurnRunner(ai, participatingCars, es);
        Attempts attempts = new Attempts(attemptsInput);

        return gameEngine.runAll(attempts, concurrentTurnRunner);
    }

    public List<String> extractWinners(List<RoundResult> allResult) {
        return gameEngine.getWinner(allResult.getLast());
    }
}
