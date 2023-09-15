package org.example.setup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
public class Dice {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Random random;

    public final static Dice INSTANCE = new Dice();
    private Dice() {
        random = new Random();
    }
    public int roll() {
        synchronized (this) {
            return random.nextInt(6) + 1;
        }
    }
}
