package racingcar.domain.strategy;

import java.util.concurrent.ThreadLocalRandom;

public enum Strategy {
    AGGRESSIVE {
        @Override
        public int move() {
            double r = ThreadLocalRandom.current().nextDouble();
            if (r < 0.6) {
                return 2;
            }
            return 0;
        }
    },

    NORMAL {
        @Override
        public int move() {
            double r = ThreadLocalRandom.current().nextDouble();
            if (r < 0.5) {
                return 1;
            }
            return 0;
        }
    },

    SAFE {
        @Override
        public int move() {
            double r = ThreadLocalRandom.current().nextDouble();
            if (r < 0.8) {
                return 1;
            }
            return 0;
        }
    };

    public abstract int move();
}
