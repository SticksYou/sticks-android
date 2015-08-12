package com.halloweenlabs.sticks.models;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Stick> sticks = new ArrayList<Stick>();
    private int rows;

    public Game(int rows) {
        this.rows = rows;

        for (int row = 1; row <= rows; row++) {
            for (int column = rows - row + 1; column <= rows + row - 1; column++) {
                sticks.add(new Stick(row, column));
            }
        }
    }

    public Iterable<Stick> getSticks() {
        return sticks;
    }

    public int getColumns() {
        return rows*2 - 1;
    }
}
