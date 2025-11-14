package racingcar;

import static racingcar.view.InputParser.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import racingcar.application.StartRacingUseCase;
import racingcar.application.dto.RoundResult;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class GameController {

    private final InputView inputView;
    private final StartRacingUseCase startRacingUseCase;
    private final OutputView outputView;

    public GameController(InputView inputView, StartRacingUseCase startRacingUseCase,
                          OutputView outputView) {
        this.inputView = inputView;
        this.startRacingUseCase = startRacingUseCase;
        this.outputView = outputView;
    }

    public void run() throws ExecutionException, InterruptedException {
        List<String> carNames = parseValidatedCarNames(inputView.carNamesReadLine());
        String attemptsInput = validateBlank(inputView.numberReadLine());

        List<RoundResult> allResult = startRacingUseCase.execute(carNames, attemptsInput);

        printGameResult(allResult);
        printWinners(allResult);
    }

    private void printGameResult(List<RoundResult> allResult) {
        outputView.printResultPrefix();
        for (RoundResult onetimeResult : allResult) {
            outputView.printResult(onetimeResult);
        }
    }

    private void printWinners(List<RoundResult> allResult) {
        List<String> winner = startRacingUseCase.extractWinners(allResult);
        outputView.printWinner(winner);
    }

}
