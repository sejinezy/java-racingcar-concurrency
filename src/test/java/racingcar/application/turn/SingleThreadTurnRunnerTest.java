package racingcar.application.turn;


import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.domain.Car;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.RoundResult;
import racingcar.domain.strategy.DefaultWinRateCalculator;
import racingcar.domain.strategy.Strategy;
import racingcar.domain.strategy.StrategyAi;
import racingcar.domain.strategy.WinRateCalculator;

class SingleThreadTurnRunnerTest {


    static class FixedStrategyAi extends StrategyAi{


        public FixedStrategyAi(WinRateCalculator winRateCalculator) {
            super(winRateCalculator);
        }

        @Override
        public Strategy decideBestStrategy(Car me, List<Car> all, int remainTurns) {
            return Strategy.NORMAL;
        }
    }

    @Test
    void run_한_턴_실행시_참여한_자동차_수만큼_결과를_반환한다() {
        ParticipatingCars cars = new ParticipatingCars(List.of("pobi", "woni"));
        Car pobi = cars.getCars().getFirst();
        Car woni = cars.getCars().get(1);

        FixedStrategyAi ai = new FixedStrategyAi(new DefaultWinRateCalculator(10));
        SingleThreadTurnRunner runner = new SingleThreadTurnRunner(cars, ai);

        int remainTurns = 3;

        RoundResult result = runner.run(remainTurns);

        assertThat(result.positions()).hasSize(2);

        assertThat(result.positions())
                .extracting(p -> p.name())
                .containsExactlyInAnyOrder("pobi", "woni");

        assertThat(result.positions())
                .anySatisfy(p -> {
                    if (p.name().equals("pobi")) {
                        assertThat(p.position()).isEqualTo(pobi.getPosition());
                    }
                });

        assertThat(result.positions())
                .anySatisfy(p -> {
                    if (p.name().equals("woni")) {
                        assertThat(p.position()).isEqualTo(woni.getPosition());
                    }
                });
    }
}