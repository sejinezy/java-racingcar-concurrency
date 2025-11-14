package racingcar;


import java.util.concurrent.ExecutionException;
import racingcar.application.StartRacingUseCase;
import racingcar.infra.random.DefaultPickRandomValue;
import racingcar.service.GameEngine;
import racingcar.domain.port.PickRandomValue;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        PickRandomValue pickRandomValue = new DefaultPickRandomValue();
        GameEngine gameEngine = new GameEngine();
        OutputView outputView = new OutputView();
        StartRacingUseCase startRacingUseCase = new StartRacingUseCase(pickRandomValue, gameEngine);

        GameController gameController = new GameController(inputView, startRacingUseCase, outputView);

        try {
            gameController.run();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
