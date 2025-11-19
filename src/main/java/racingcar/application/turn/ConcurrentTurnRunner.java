package racingcar.application.turn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import racingcar.domain.RoundResult;
import racingcar.domain.Car;
import racingcar.domain.CarPosition;
import racingcar.domain.ParticipatingCars;
import racingcar.domain.strategy.StrategyAi;

public class ConcurrentTurnRunner implements TurnRunner {

    private static final String ERR_INTERRUPT = "레이스가 인터럽트 되었습니다.";
    private static final String ERR_EXECUTION = "레이스 작업 중 예외 발생";

    private final StrategyAi ai;
    private final ParticipatingCars cars;
    private final ExecutorService es;

    public ConcurrentTurnRunner(StrategyAi ai, ParticipatingCars cars, ExecutorService es) {
        this.ai = ai;
        this.cars = cars;
        this.es = es;
    }

    @Override
    public RoundResult run(int remainTurns) {
        try {
            List<Callable<CarPosition>> tasks = createTasks(remainTurns);
            List<Future<CarPosition>> futures = es.invokeAll(tasks);
            return resultOneTime(futures);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ERR_INTERRUPT, e);

        } catch (ExecutionException e) {
            throw new RuntimeException(ERR_EXECUTION, e.getCause());
        }
    }

    private List<Callable<CarPosition>> createTasks(int remainTurns) {
        List<Callable<CarPosition>> tasks = new ArrayList<>();

        for (Car car : cars.getCars()) {
            ConcurrentTurnTask turnRunner = new ConcurrentTurnTask(car, ai, cars, remainTurns);
            tasks.add(turnRunner);
        }
        return tasks;
    }

    private static RoundResult resultOneTime(List<Future<CarPosition>> futures)
            throws ExecutionException, InterruptedException {
        List<CarPosition> gameResult = new ArrayList<>();

        for (Future<CarPosition> future : futures) {
            gameResult.add(future.get());
        }
        return new RoundResult(gameResult);
    }
}
