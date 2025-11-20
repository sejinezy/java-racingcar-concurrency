package racingcar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import racingcar.application.engine.GameEngine;
import racingcar.application.usecase.StartRacingUseCase;
import racingcar.domain.strategy.DefaultWinRateCalculator;
import racingcar.domain.strategy.WinRateCalculator;
import racingcar.ui.controller.GameController;
import racingcar.domain.strategy.StrategyAi;
import racingcar.application.engine.MultiThreadGameEngine;
import racingcar.ui.view.InputView;
import racingcar.ui.view.OutputView;

public class Application {
    private static final String SHUTDOWN_STAGE1_ATTEMPT = "[POOL] (1/3) 정상 종료 시도";
    private static final String SHUTDOWN_STAGE2_FORCED = "[POOL] (2/3) 정상 종료 실패 -> 강제 종료 시도";
    private static final String SHUTDOWN_STAGE3_UNTERMINATED = "[POOL] (3/3) 서비스가 종료되지 않았습니다.";
    private static final int TIME_OUT = 10;
    private static final int SIMULATION_COUNT = 2000;
    private final static int THREADS = 8;


    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(THREADS);
        InputView inputView = new InputView();
        WinRateCalculator winRateCalculator = new DefaultWinRateCalculator(SIMULATION_COUNT);
        StrategyAi ai = new StrategyAi(winRateCalculator);
        GameEngine gameEngine = new MultiThreadGameEngine();
        OutputView outputView = new OutputView();
        StartRacingUseCase startRacingUseCase = new StartRacingUseCase(ai, gameEngine, es);
        GameController gameController = new GameController(inputView, startRacingUseCase, outputView);

        try {
            gameController.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        } finally {
            shutdownAndAwaitTermination(es);
        }
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown();

        try {
            System.out.println(SHUTDOWN_STAGE1_ATTEMPT);
            if (!es.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                System.out.println(SHUTDOWN_STAGE2_FORCED);
                es.shutdownNow();

                if (!es.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                    System.out.println(SHUTDOWN_STAGE3_UNTERMINATED);
                }
            }
        } catch (InterruptedException ex) {
            es.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
