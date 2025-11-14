package racingcar.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String CAR_NAMES_PREFIX = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private static final String NUMBER_PREFIX = "시도할 횟수는 몇 회인가요?";

    public String carNamesReadLine() {
        System.out.println(CAR_NAMES_PREFIX);
        return Console.readLine();
    }

    public String numberReadLine() {
        System.out.println(NUMBER_PREFIX);
        return Console.readLine();
    }

}
