package racingcar.domain;

import java.util.List;

public record RoundResult(List<CarPosition> positions) {
    public RoundResult {
        positions = List.copyOf(positions);
    }
}
