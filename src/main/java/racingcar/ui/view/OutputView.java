package racingcar.ui.view;

import java.util.List;
import racingcar.domain.CarPosition;
import racingcar.domain.RoundResult;

public class OutputView {

    private static final String RESULT_PREFIX = "실행 결과";
    private static final String CAR_POSITION_MARKER = "-";
    private static final String WINNER = "최종 우승자 : ";
    private static final String RESULT_ENTRY_DELIMITER = " : ";
    private static final String WINNER_DELIMITER = ", ";

    public void printResult(RoundResult result) {

        for (CarPosition participatedCar : result.positions()) {
            System.out.println(participatedCar.name() + RESULT_ENTRY_DELIMITER + CAR_POSITION_MARKER.repeat(
                    participatedCar.position()));
        }
        System.out.println();

    }

    public void printResultPrefix() {
        System.out.println(RESULT_PREFIX);
    }

    public void printWinner(List<String> winnerNames) {
        System.out.print(WINNER);
        System.out.println(String.join(WINNER_DELIMITER, winnerNames));
    }
}

