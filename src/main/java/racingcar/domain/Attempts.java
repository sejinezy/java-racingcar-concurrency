package racingcar.domain;

public class Attempts {

    private static final String ERR_NUMBER_INTEGER = "정수 숫자만 가능합니다.";
    private static final String ERR_NUMBER_POSITIVE = "1 이상이어야 합니다.";

    private final int number;

    public Attempts(String numberInput) {
        int number = parseInt(numberInput);
        validatePositive(number);
        this.number = number;
    }

    private static int parseInt(String numberInput) {
        try {
            return Integer.parseInt(numberInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERR_NUMBER_INTEGER);
        }
    }

    private static boolean isPositive(int number) {
        return number > 0;
    }

    private void validatePositive(int number) {
        if (!isPositive(number)) {
            throw new IllegalArgumentException(ERR_NUMBER_POSITIVE);
        }
    }

    public int getNumber() {
        return number;
    }
}
