package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {


    @Test
    void 기능_테스트() {
        assertSimpleTest(() -> {
            run("pobi,woni", "1");
            String output = output();

            assertThat(output).contains(
                    "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)",
                    "시도할 횟수는 몇 회인가요?",
                    "실행 결과"
            );

            assertThat(output).contains("pobi :");
            assertThat(output).contains("woni :");

            assertThat(output).contains("최종 우승자 : ");

            assertThat(output).containsAnyOf(
                    "최종 우승자 : pobi",
                    "최종 우승자 : woni",
                    "최종 우승자 : pobi, woni"
            );

        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->{
            run("pobi,javaji", "1");
            assertThat(output())
                    .contains("[ERROR] 이름은 5자 이하만 가능합니다.");
                }
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
