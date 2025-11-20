package racingcar.domain;


import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoundResultTest {

    @Test
    void positions_초기값을_그대로_들고_있는다() {
        List<CarPosition> original = List.of(new CarPosition("a", 1), new CarPosition("b", 2));

        RoundResult result = new RoundResult(original);

        assertThat(result.positions()).containsExactlyElementsOf(original);
    }

    @Test
    void 원본_리스트를_수정해도_RoundResult는_영향을_받지_않는다(){
        List<CarPosition> original = new ArrayList<>();
        original.add(new CarPosition("a", 1));

        RoundResult result = new RoundResult(original);

        original.add(new CarPosition("b", 2));

        assertThat(result.positions()).hasSize(1).containsExactly(new CarPosition("a", 1));
    }

}