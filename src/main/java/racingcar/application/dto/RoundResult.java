package racingcar.application.dto;

import java.util.List;
import racingcar.domain.CarPosition;

public record RoundResult(List<CarPosition> positions) {
    public RoundResult{
        positions = List.copyOf(positions);
    }
}
