package racingcar;


import java.util.concurrent.ExecutionException;
import racingcar.application.StartRacingUseCase;
import racingcar.ui.controller.GameController;
import racingcar.domain.strategy.StrategyAi;
import racingcar.application.multithread.MultiThreadGameEngine;
import racingcar.ui.view.InputView;
import racingcar.ui.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        StrategyAi ai = new StrategyAi(2000);
        MultiThreadGameEngine gameEngine = new MultiThreadGameEngine();
        OutputView outputView = new OutputView();
        StartRacingUseCase startRacingUseCase = new StartRacingUseCase(ai, gameEngine);

        GameController gameController = new GameController(inputView, startRacingUseCase, outputView);

        try {
            gameController.run();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
