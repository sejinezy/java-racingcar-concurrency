package racingcar.application;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import racingcar.domain.RoundResult;
import racingcar.domain.Attempts;
import racingcar.domain.multithread.ConcurrentTurnRunner;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.StrategyAi;
import racingcar.application.multithread.MultiThreadGameEngine;

public class StartRacingUseCase {

    private final StrategyAi ai;
    private final MultiThreadGameEngine gameEngine;

    public StartRacingUseCase(StrategyAi ai, MultiThreadGameEngine gameEngine) {
        this.ai = ai;
        this.gameEngine = gameEngine;
    }


    public List<RoundResult> execute(List<String> carNames, String attemptsInput)
            throws InterruptedException, ExecutionException {
        ParticipatingCars participatingCars = new ParticipatingCars(carNames);

        ExecutorService es = Executors.newFixedThreadPool(8);

        ConcurrentTurnRunner concurrentTurnRunner = new ConcurrentTurnRunner(ai, participatingCars, es);

        Attempts attempts = new Attempts(attemptsInput);

        return gameEngine.runAll(attempts, concurrentTurnRunner);
    }

    public List<String> extractWinners(List<RoundResult> allResult) {
        return gameEngine.getWinner(allResult.getLast());

    }
}
