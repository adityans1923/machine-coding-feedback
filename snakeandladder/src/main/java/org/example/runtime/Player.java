package org.example.runtime;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player {
    private final String id;
    private final String name;
    private Integer currentCell;

    public Player(String name) {
        this.id  = UUID.randomUUID().toString();
        this.currentCell = 1;
        this.name = name;
    }
}
