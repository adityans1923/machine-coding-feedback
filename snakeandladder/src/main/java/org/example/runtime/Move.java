package org.example.runtime;

import lombok.Getter;
import org.example.setup.ActionType;

@Getter
public class Move {
    private final Player player;
    private final Integer from;
    private final Integer to;
    private final ActionType type;

    public Move(Player player, Integer from, Integer to, ActionType type) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.type = type;
    }
}
