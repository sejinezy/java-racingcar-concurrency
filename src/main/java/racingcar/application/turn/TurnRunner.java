package racingcar.application.turn;

import java.util.concurrent.ExecutionException;
import racingcar.domain.RoundResult;

public interface TurnRunner {

    RoundResult run(int remainTurns) throws InterruptedException, ExecutionException;

}
