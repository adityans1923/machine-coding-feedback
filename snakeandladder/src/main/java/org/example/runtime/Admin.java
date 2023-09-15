package org.example.runtime;

import lombok.Getter;
import lombok.Setter;
import org.example.setup.Action;
import org.example.setup.ActionType;

@Setter
@Getter
public final class Admin {
    private BoardConfig config;
    private GameManager manager;

    public Admin(GameManager manager) {
        config = new BoardConfig(10);
        this.manager = manager;
    }

    public void createNewBoard(Integer n) {
        System.out.println("Previous Board is getting overridden, you will have to add actions manually");
        config = new BoardConfig(n);
    }

    public void addNewAction(Integer start, Integer end) {
        ActionType type = start > end ? ActionType.Snake : ActionType.Ladder;
        int n  =config.getBoardSize();
        n = n*n;
        if (start > 1 && start < n && end >=1 && end <= n) {
            Action action = new Action(type, start, end);
            config.getActions().add(action);
        } else {
            String invalidAction = "Invalid action : from {0} to {1} for board of size: {2}";
            System.out.printf((invalidAction) + "%n", start, end, config.getBoardSize());
        }
    }
}
