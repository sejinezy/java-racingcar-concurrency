package racingcar.application.engine;

import java.util.List;
import java.util.concurrent.ExecutionException;
import racingcar.domain.Attempts;
import racingcar.domain.RoundResult;
import racingcar.application.turn.TurnRunner;

public interface GameEngine {

    List<RoundResult> runAll(Attempts attempts, TurnRunner concurrentTurnRunner)
            throws ExecutionException, InterruptedException;

    List<String> getWinner(RoundResult lastResult);
}
