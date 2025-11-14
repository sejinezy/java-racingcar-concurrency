package racingcar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import racingcar.domain.Attempts;
import racingcar.domain.CarPosition;
import racingcar.domain.ConcurrentTurnRunner;
import racingcar.application.dto.RoundResult;

public class GameEngine {

    public List<RoundResult> runAll(Attempts attempts, ConcurrentTurnRunner concurrentTurnRunner)
            throws ExecutionException, InterruptedException {

        List<RoundResult> gameResults = new ArrayList<>();
        for (int i = 0; i < attempts.getNumber(); i++) {
            gameResults.add(concurrentTurnRunner.concurrentTurnRunner());
        }
        return gameResults;
    }

    public List<String> getWinner(RoundResult lastResult) {
        List<String> winnerNames = new ArrayList<>();

        int maxPosition = getMaxPosition(lastResult);

        for (CarPosition participatedCar : lastResult.positions()) {
            if (participatedCar.position() == maxPosition) {
                winnerNames.add(participatedCar.name());
            }
        }
        return winnerNames;
    }

    private static int getMaxPosition(RoundResult lastResult) {
        int maxPosition = 0;
        for (CarPosition position : lastResult.positions()) {
            if (position.position() > maxPosition) {
                maxPosition = position.position();
            }
        }
        return maxPosition;
    }
}
