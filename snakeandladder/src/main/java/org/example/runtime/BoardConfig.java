package org.example.runtime;

import lombok.Getter;
import lombok.Setter;
import org.example.setup.Action;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardConfig {
    private final int boardSize;
    List<Action> actions;

    public BoardConfig(Integer n) {
        this.boardSize = n;
        actions = new ArrayList<>();
    }
}
