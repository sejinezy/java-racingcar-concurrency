package racingcar.domain;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.domain.strategy.Strategy;

class CarTest {

    @Test
    void 이름이_5자_초과인_경우_예외() {
        assertThatThrownBy(() -> new Car("sejinn"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 5자 이하만 가능합니다.");
    }

    @Test
    void 이름이_5자_이하인_경우_객체_생성() {
        Car car = new Car("sejin");
        assertThat(car.getName()).isEqualTo("sejin");
    }

    @Test
    void 전략에_따라_자동차가_이동한다() {
        Car car1 = new Car("pobi");
        car1.moveAccordingTo(Strategy.AGGRESSIVE);
        Assertions.assertThat(car1.getPosition()).isIn(0, 2);

        Car car2 = new Car("woni");
        car2.moveAccordingTo(Strategy.NORMAL);
        Assertions.assertThat(car2.getPosition()).isIn(0, 1);

        Car car3 = new Car("jun");
        car3.moveAccordingTo(Strategy.SAFE);
        Assertions.assertThat(car3.getPosition()).isIn(0, 1);
    }

}