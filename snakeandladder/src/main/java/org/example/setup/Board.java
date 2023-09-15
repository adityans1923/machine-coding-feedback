package org.example.setup;

import lombok.Getter;
import lombok.Setter;
import org.example.runtime.BoardConfig;

import java.util.Map;

@Getter
@Setter
public class Board {
    int size;
    Map<Integer, Action> actions;
    Dice dice = Dice.INSTANCE;
    public Board( BoardConfig config) {
        this.size = config.getBoardSize();
        for (int i=0;i< config.getActions().size();i++) {
            actions.put(config.getActions().get(i).getFromCell(), config.getActions().get(i));
        }
    }
}
