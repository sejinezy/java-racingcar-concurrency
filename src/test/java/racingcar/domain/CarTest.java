package racingcar.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
    void 앞으로_이동하면_포지션이_1_증가한다() {
        Car car = new Car("sejin");
        car.moveForward();
        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    void 여러번_이동하면_누적_증가한다() {
        Car car = new Car("sejin");
        car.moveForward();
        car.moveForward();
        car.moveForward();
        assertThat(car.getPosition()).isEqualTo(3);
    }
}