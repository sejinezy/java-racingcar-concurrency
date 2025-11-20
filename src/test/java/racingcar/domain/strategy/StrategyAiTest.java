package racingcar.domain.strategy;


import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.domain.Car;

class StrategyAiTest {

    static class FakeWinRateCalculator1 implements WinRateCalculator{

        @Override
        public double calculate(Car me, List<Car> all, int remainTurns, Strategy s) {
            if (s == Strategy.AGGRESSIVE) {
                return 0.8;
            }
            if (s == Strategy.NORMAL) {
                return 0.5;
            }
            if (s == Strategy.SAFE) {
                return 0.3;
            }
            return 0.0;
        }
    }

    static class FakeWinRateCalculator2 implements WinRateCalculator{

        @Override
        public double calculate(Car me, List<Car> all, int remainTurns, Strategy s) {
            return 0.5;
        }
    }

    @Test
    void 가장_승률이_높은_전략을_선택한다() {
        WinRateCalculator fakeWinRateCalculator1 = new FakeWinRateCalculator1();
        StrategyAi ai = new StrategyAi(fakeWinRateCalculator1);
        Car me = new Car("me");
        List<Car> all = List.of(me);
        Strategy best = ai.decideBestStrategy(me, all, 5);
        Assertions.assertThat(best).isEqualTo(Strategy.AGGRESSIVE);

    }

    @Test
    void 승률이_동일하면_ENUM_정의_순서를_따라_선택한다() {
        WinRateCalculator fakeWinRateCalculator2 = new FakeWinRateCalculator2();
        StrategyAi ai = new StrategyAi(fakeWinRateCalculator2);
        Car me = new Car("me");
        List<Car> all = List.of(me);
        Strategy best = ai.decideBestStrategy(me, all, 5);
        Assertions.assertThat(best).isEqualTo(Strategy.AGGRESSIVE);

    }

}