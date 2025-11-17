package racingcar.benchmark;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import racingcar.domain.Attempts;
import racingcar.application.turn.ConcurrentTurnRunner;
import racingcar.domain.ParticipatingCars;
import racingcar.application.turn.SingleThreadTurnRunner;
import racingcar.domain.strategy.StrategyAi;
import racingcar.application.engine.MultiThreadGameEngine;
import racingcar.application.engine.SingleThreadGameEngine;

public class RacingBenchmark {

    private static final int WARMUP = 3;
    private static final int MEASURE = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> carNames = List.of("car1", "car2", "car3", "car4", "car5", "car6", "car7", "car8",
                "car9", "car10", "car11", "car12", "car13", "car14", "car15", "car16",
                "car17", "car18", "car19", "car20", "car21", "car22", "car23", "car24",
                "car25", "car26", "car27", "car28", "car29", "car30", "car31", "car32");

        Attempts attempts = new Attempts("150");

        ParticipatingCars singleCars = new ParticipatingCars(carNames);
        ParticipatingCars concurrentCars = new ParticipatingCars(carNames);

        StrategyAi singleAi = new StrategyAi(5000);
        StrategyAi multiAi = new StrategyAi(5000);

        long singleAvg = benchmarkSingleThread(singleCars, attempts, singleAi);
        long concurrentAvg = benchmarkMultiThread(concurrentCars, attempts, multiAi);

        System.out.println("=== Result (avg, millis) ===");
        System.out.println("Single-thread : " + singleAvg);
        System.out.println("Multi-thread : " + concurrentAvg);

    }

    private static long benchmarkSingleThread(ParticipatingCars cars,
                                              Attempts attempts,
                                              StrategyAi ai) throws ExecutionException, InterruptedException {

        SingleThreadTurnRunner runner = new SingleThreadTurnRunner(cars, ai);
        SingleThreadGameEngine engine = new SingleThreadGameEngine();

        for (int i = 0; i < WARMUP; i++) {
            engine.runAll(attempts, runner);
        }

        long total = 0;
        for (int i = 0; i < MEASURE; i++) {
            long start = System.nanoTime();
            engine.runAll(attempts, runner);
            long end = System.nanoTime();
            total += (end - start);
        }
        long avgNanos = total / MEASURE;
        return avgNanos / 1_000_000;

    }

    private static long benchmarkMultiThread(ParticipatingCars cars,
                                             Attempts attempts,
                                             StrategyAi ai) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(8);
        ConcurrentTurnRunner concurrentTurnRunner = new ConcurrentTurnRunner(ai,cars,es);
        MultiThreadGameEngine gameEngine = new MultiThreadGameEngine();

        for (int i = 0; i < WARMUP; i++) {
            gameEngine.runAll(attempts, concurrentTurnRunner);
        }

        long total = 0;
        for (int i = 0; i < MEASURE; i++) {
            long start = System.nanoTime();
            gameEngine.runAll(attempts, concurrentTurnRunner);
            long end = System.nanoTime();
            total += (end - start);
        }

        es.close();

        long avgNanos = total / MEASURE;
        return avgNanos / 1_000_000;
    }
}
