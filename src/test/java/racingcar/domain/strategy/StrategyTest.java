package racingcar.domain.strategy;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StrategyTest {

    @Test
    void AGGRESSIVE_랜덤값이_임계값보다_작으면_2칸_이동한다() {
        Assertions.assertThat(Strategy.AGGRESSIVE.decideMove(0.5))
                .isEqualTo(2);
    }

    @Test
    void AGGRESSIVE_랜덤값이_임계값_이상이면_움직이지_않는다() {
        Assertions.assertThat(Strategy.AGGRESSIVE.decideMove(0.7))
                .isEqualTo(0);
    }

    @Test
    void NORMAL_랜덤값이_임계값보다_작으면_1칸_이동한다() {
        Assertions.assertThat(Strategy.NORMAL.decideMove(0.4))
                .isEqualTo(1);
    }

    @Test
    void NORMAL_랜덤값이_임계값_이상이면_움직이지_않는다() {
        Assertions.assertThat(Strategy.NORMAL.decideMove(0.6))
                .isEqualTo(0);

    }

    @Test
    void SAFE_랜덤값이_임계값보다_작으면_1칸_이동한다() {
        Assertions.assertThat(Strategy.SAFE.decideMove(0.7))
                .isEqualTo(1);
    }

    @Test
    void SAFE_랜덤값이_임계값_이상이면_움직이지_않는다() {
        Assertions.assertThat(Strategy.SAFE.decideMove(0.9))
                .isEqualTo(0);

    }

}