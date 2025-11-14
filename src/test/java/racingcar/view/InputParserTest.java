package racingcar.view;

import static org.assertj.core.api.Assertions.*;
import static racingcar.view.InputParser.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class InputParserTest {

    @Test
    void 빈_값이_들어오면_예외() {
        assertThatThrownBy(() -> validateBlank(null))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> validateBlank(""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> validateBlank("  "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 띄어쓰기가_포함되어_있으면_공백_제거해서_반환한다() {
        String validateBlank = validateBlank(" 3 ");
        assertThat(validateBlank).isEqualTo("3");
    }

    @Test
    void 구분자를_기준으로_파싱한다() {
        List<String> parsedCarNames = parseValidatedCarNames("pobi,woni");
        assertThat(parsedCarNames).containsExactly("pobi", "woni");
    }

    @Test
    void 구분자를_기준으로_파싱할때_빈_토큰이_들어오면_예외() {
        assertThatThrownBy(() -> parseValidatedCarNames(",pobi"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> parseValidatedCarNames("pobi,,woni"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> parseValidatedCarNames("woni,"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 파싱할때_띄어쓰기가_있으면_제거해서_반환한다() {
        List<String> parsedCarNames = parseValidatedCarNames(" pobi , woni ");
        assertThat(parsedCarNames).containsExactly("pobi", "woni");
    }

}