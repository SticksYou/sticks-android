package com.halloweenlabs.sticks.models;

public class Stick {
    private boolean available;
    private int row;
    private int column;

    public Stick(int row, int column) {
        this.row = row;
        this.column = column;
        this.available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
