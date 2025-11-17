package racingcar.benchmark;

public class CpuWork {

    private static volatile long BLACK_HOLE = 0;

    public static void heavyWork() {
        long x = 0;
        for (int i = 0; i < 10_000; i++) {
            x += i;
        }
        BLACK_HOLE = x;
    }

    public static long getBlackHole() {
        return BLACK_HOLE;
    }
}
