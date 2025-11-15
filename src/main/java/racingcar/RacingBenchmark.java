package racingcar;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import racingcar.domain.Attempts;
import racingcar.domain.multithread.ConcurrentTurnRunner;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.port.PickRandomValue;
import racingcar.domain.singlethread.SingleThreadTurnRunner;
import racingcar.infra.random.DefaultPickRandomValue;
import racingcar.service.multithread.MultiThreadGameEngine;
import racingcar.service.singlethread.SingleThreadGameEngine;
import racingcar.util.CpuWork;

public class RacingBenchmark {

    private static final int WARMUP = 3;
    private static final int MEASURE = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> carNames = List.of("car1", "car2", "car3", "car4", "car5", "car6", "car7", "car8", "car9", "car10",
                "car11", "car12", "car13", "car14", "car15", "car16",
                "car17", "car18", "car19", "car20", "car21", "car22", "car23", "car24", "car25", "car26", "car27",
                "car28", "car29", "car30", "car31",
                "car32", "car33", "car34", "car35", "car36", "car37", "car38", "car39", "car40");

        Attempts attempts = new Attempts("1000");

        ParticipatingCars singleCars = new ParticipatingCars(carNames);
        ParticipatingCars concurrentCars = new ParticipatingCars(carNames);

        DefaultPickRandomValue pickRandomValue1 = new DefaultPickRandomValue();
        DefaultPickRandomValue pickRandomValue2 = new DefaultPickRandomValue();

        // 싱글 스레드 벤치 마크
        long singleAvg = benchmarkSingleThread(singleCars, attempts, pickRandomValue1);

        // 멀티 스레드 벤치 마크
        long concurrentAvg = benchmarkMultiThread(concurrentCars, attempts, pickRandomValue2);

        System.out.println("=== Result (avg, millis) ===");
        System.out.println("Single-thread : " + singleAvg);
        System.out.println("Multi-thread : " + concurrentAvg);

        System.out.println(CpuWork.getBlackHole());

    }

    private static long benchmarkSingleThread(ParticipatingCars cars,
                                              Attempts attempts,
                                              PickRandomValue random) {

        SingleThreadTurnRunner runner = new SingleThreadTurnRunner(cars, random);
        SingleThreadGameEngine engine = new SingleThreadGameEngine();

        //워밍업
        for (int i = 0; i < WARMUP; i++) {
            engine.runAll(attempts, runner);
        }

        //측정
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
                                             PickRandomValue random) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(8);
        ConcurrentTurnRunner concurrentTurnRunner = new ConcurrentTurnRunner(random, cars, es);
        MultiThreadGameEngine gameEngine = new MultiThreadGameEngine();

        // 워밍업
        for (int i = 0; i < WARMUP; i++) {
            gameEngine.runAll(attempts, concurrentTurnRunner);
        }

        // 측정
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
