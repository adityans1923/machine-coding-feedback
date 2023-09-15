package org.example.setup;

import lombok.Getter;

@Getter
public class Action {
    private final ActionType type;
    private final Integer fromCell;
    private final Integer toCell;

    public Action(ActionType type, Integer fromCell, Integer toCell) {
        this.type = type;
        this.fromCell = fromCell;
        this.toCell = toCell;
    }
}
