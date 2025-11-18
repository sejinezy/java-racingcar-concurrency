package racingcar;


import java.util.concurrent.ExecutionException;
import racingcar.application.engine.GameEngine;
import racingcar.application.usecase.StartRacingUseCase;
import racingcar.ui.controller.GameController;
import racingcar.domain.strategy.StrategyAi;
import racingcar.application.engine.MultiThreadGameEngine;
import racingcar.ui.view.InputView;
import racingcar.ui.view.OutputView;

public class Application {
    private static final int SIMULATION_COUNT = 2000;

    public static void main(String[] args) {
        InputView inputView = new InputView();
        StrategyAi ai = new StrategyAi(SIMULATION_COUNT);
        GameEngine gameEngine = new MultiThreadGameEngine();
        OutputView outputView = new OutputView();
        StartRacingUseCase startRacingUseCase = new StartRacingUseCase(ai, gameEngine);
        GameController gameController = new GameController(inputView, startRacingUseCase, outputView);

        gameController.run();
    }
}
