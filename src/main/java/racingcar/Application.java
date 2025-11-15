package racingcar;


import java.util.concurrent.ExecutionException;
import racingcar.application.StartRacingUseCase;
import racingcar.infra.random.DefaultPickRandomValue;
import racingcar.service.multithread.MultiThreadGameEngine;
import racingcar.domain.port.PickRandomValue;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        PickRandomValue pickRandomValue = new DefaultPickRandomValue();
        MultiThreadGameEngine gameEngine = new MultiThreadGameEngine();
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
