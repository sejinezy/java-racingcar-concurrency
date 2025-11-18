package racingcar.application.engine;

import java.util.ArrayList;
import java.util.List;
import racingcar.domain.Attempts;
import racingcar.domain.CarPosition;
import racingcar.application.turn.TurnRunner;
import racingcar.domain.RoundResult;

public class MultiThreadGameEngine implements GameEngine {

    @Override
    public List<RoundResult> runAll(Attempts attempts, TurnRunner concurrentTurnRunner) {

        List<RoundResult> gameResults = new ArrayList<>();
        for (int i = 0; i < attempts.getNumber(); i++) {
            gameResults.add(concurrentTurnRunner.run(attempts.getNumber()-i));
        }
        return gameResults;
    }

    @Override
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
