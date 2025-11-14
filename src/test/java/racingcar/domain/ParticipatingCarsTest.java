package racingcar.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParticipatingCarsTest {

    @Test
    void 동일한_이름의_자동차가_있으면_예외() {
        List<String> cars = new ArrayList<>();
        cars.add("pobi");
        cars.add("woni");
        cars.add("pobi");

        assertThatThrownBy(() -> new ParticipatingCars(cars))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("동일한 이름의 자동차는 같은 게임에 참여할 수 없습니다.");
    }

    @Test
    void 두대_미만의_자동차가_참여하면_예외() {
        List<String> cars = new ArrayList<>();
        cars.add("pobi");

        assertThatThrownBy(() -> new ParticipatingCars(cars))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 대 이상의 자동차가 참여해야 합니다.");
    }

    @Test
    void 서로_다른_이름의_자동차가_참여하면_객체_생성() {
        List<String> cars = List.of("pobi", "woni");
        ParticipatingCars participatingCars = new ParticipatingCars(cars);
        List<Car> carList = participatingCars.getCars();

        assertThat(carList).extracting(Car::getName)
                .containsExactly("pobi", "woni");
    }


}