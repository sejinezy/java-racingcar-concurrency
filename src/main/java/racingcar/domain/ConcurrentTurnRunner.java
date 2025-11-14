package racingcar.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import racingcar.application.dto.RoundResult;
import racingcar.domain.port.PickRandomValue;

public class ConcurrentTurnRunner {

    private final PickRandomValue pickRandomValue;
    private final ParticipatingCars participatingCars;
    private final ExecutorService es;

    public ConcurrentTurnRunner(PickRandomValue pickRandomValue, ParticipatingCars participatingCars,
                                ExecutorService es) {
        this.pickRandomValue = pickRandomValue;
        this.participatingCars = participatingCars;
        this.es = es;
    }


    public RoundResult concurrentTurnRunner()
            throws InterruptedException, ExecutionException {

        List<Callable<CarPosition>> tasks = new ArrayList<>();

        for (Car car : participatingCars.getCars()) {
            RacingTurnRunner turnRunner = new RacingTurnRunner(car, pickRandomValue);
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
