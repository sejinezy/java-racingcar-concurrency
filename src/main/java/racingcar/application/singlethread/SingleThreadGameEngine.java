package racingcar.application.singlethread;

import java.util.ArrayList;
import java.util.List;
import racingcar.domain.RoundResult;
import racingcar.domain.Attempts;
import racingcar.domain.CarPosition;
import racingcar.domain.singlethread.SingleThreadTurnRunner;

public class SingleThreadGameEngine {

    public List<RoundResult> runAll(Attempts attempts, SingleThreadTurnRunner racingGame) {
        List<RoundResult> gameResults = new ArrayList<>();

        for (int i = 0; i < attempts.getNumber(); i++) {
            gameResults.add(racingGame.runOneTime(attempts.getNumber()-i));
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
