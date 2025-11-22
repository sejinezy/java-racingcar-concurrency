package racingcar.application.turn;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import racingcar.domain.Car;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.DefaultWinRateCalculator;
import racingcar.domain.strategy.Strategy;
import racingcar.domain.strategy.StrategyAi;
import racingcar.domain.strategy.WinRateCalculator;

public class ConcurrentTurnRunnerExceptionTest {

    static class FailingStrategyAi extends StrategyAi {

        public FailingStrategyAi(WinRateCalculator winRateCalculator) {
            super(winRateCalculator);
        }

        @Override
        public Strategy decideBestStrategy(Car me, List<Car> all, int remainTurns) {
            throw new IllegalStateException("boom");
        }
    }

    private final ExecutorService es = Executors.newFixedThreadPool(4);

    @AfterEach
    void shutdown() {
        es.shutdown();
    }

    @Test
    void 전략_결정_중_예외가_발생하면_레이스_작업_예외로_래핑한다() {
        ParticipatingCars cars = new ParticipatingCars(List.of("pobi", "jun"));
        FailingStrategyAi failingAi = new FailingStrategyAi(new DefaultWinRateCalculator(10));

        ConcurrentTurnRunner runner = new ConcurrentTurnRunner(failingAi, cars, es);

        Assertions.assertThatThrownBy(() -> runner.run(3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("레이스 작업 중 예외 발생")
                .cause()
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("boom");
    }

}
