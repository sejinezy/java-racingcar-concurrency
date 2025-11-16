package racingcar.domain.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import racingcar.application.dto.RoundResult;
import racingcar.domain.Car;
import racingcar.domain.CarPosition;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.StrategyAi;

public class ConcurrentTurnRunner {

    private final StrategyAi ai;
    private final ParticipatingCars cars;
    private final ExecutorService es;

    public ConcurrentTurnRunner(StrategyAi ai, ParticipatingCars cars, ExecutorService es) {
        this.ai = ai;
        this.cars = cars;
        this.es = es;
    }


    public RoundResult concurrentTurnRunner(int remainTurns)
            throws InterruptedException, ExecutionException {

        List<Callable<CarPosition>> tasks = new ArrayList<>();

        for (Car car : cars.getCars()) {
            RacingTurnRunner turnRunner = new RacingTurnRunner(car, ai, cars, remainTurns);
            tasks.add(turnRunner);
        }

        List<Future<CarPosition>> futures = es.invokeAll(tasks);
        return resultOneTime(futures);
    }

    private static  RoundResult resultOneTime(List<Future<CarPosition>> futures) throws ExecutionException, InterruptedException {
        List<CarPosition> gameResult = new ArrayList<>();

        for (Future<CarPosition> future : futures) {
            gameResult.add(future.get());
        }
        return new RoundResult(gameResult);
    }
}
