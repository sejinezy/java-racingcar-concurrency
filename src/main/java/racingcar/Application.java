package racingcar;


import java.util.concurrent.ExecutionException;
import racingcar.application.StartRacingUseCase;
import racingcar.domain.strategy.StrategyAi;
import racingcar.service.multithread.MultiThreadGameEngine;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        StrategyAi ai = new StrategyAi(1000);
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
