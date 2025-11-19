package racingcar.application.usecase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import racingcar.application.engine.GameEngine;
import racingcar.domain.RoundResult;
import racingcar.domain.Attempts;
import racingcar.application.turn.TurnRunner;
import racingcar.application.turn.ConcurrentTurnRunner;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.StrategyAi;

public class StartRacingUseCase {

    private final StrategyAi ai;
    private final GameEngine gameEngine;
    private final ExecutorService es;

    public StartRacingUseCase(StrategyAi ai, GameEngine gameEngine, ExecutorService es) {
        this.ai = ai;
        this.gameEngine = gameEngine;
        this.es = es;
    }

    public List<RoundResult> execute(List<String> carNames, String attemptsInput) {
        ParticipatingCars participatingCars = new ParticipatingCars(carNames);
        TurnRunner concurrentTurnRunner = new ConcurrentTurnRunner(ai, participatingCars, es);
        Attempts attempts = new Attempts(attemptsInput);

        return gameEngine.runAll(attempts, concurrentTurnRunner);
    }

    public List<String> extractWinners(List<RoundResult> allResult) {
        return gameEngine.getWinner(allResult.getLast());
    }
}
