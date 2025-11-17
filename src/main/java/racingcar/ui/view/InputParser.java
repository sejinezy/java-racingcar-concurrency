package racingcar.ui.view;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

    private static final String ERR_IS_BLANK = "빈 값은 허용되지 않습니다.";
    private static final String REGEX = ",";

    private InputParser() {
    }

    public static String validateBlank(String raw) {
        if (isBlank(raw)) {
            throw new IllegalArgumentException(ERR_IS_BLANK);
        }
        return raw.trim();
    }

    private static boolean isBlank(String raw) {
        return raw == null || raw.isBlank();
    }

    private static String[] splitByComma(String raw) {
        return raw.split(REGEX, -1);
    }

    public static List<String> parseValidatedCarNames(String raw) {
        validateBlank(raw);
        List<String> carNames = new ArrayList<>();

        String[] split = splitByComma(raw);
        for (String carName : split) {
            String validatedInput = validateBlank(carName);
            carNames.add(validatedInput);
        }
        return carNames;
    }
}
