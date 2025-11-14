package racingcar.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AttemptsTest {

    @Test
    void 숫자가_아닌_경우_예외() {
        assertThatThrownBy(() -> new Attempts("a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정수 숫자만 가능합니다.");
    }

    @Test
    void 정수가_아닌_경우_예외() {
        assertThatThrownBy(() -> new Attempts("1.5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정수 숫자만 가능합니다.");
    }

    @Test
    void 음수인_경우_예외() {
        assertThatThrownBy(() -> new Attempts("-1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 이상이어야 합니다.");
    }

    @Test
    void 양수가_아닌_경우_예외() {
        assertThatThrownBy(() -> new Attempts("0"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 이상이어야 합니다.");
    }

    @Test
    void 양의_정수인_경우_객체_생성() {
        Attempts attempts = new Attempts("2");
        assertThat(attempts.getNumber()).isEqualTo(2);
    }

}