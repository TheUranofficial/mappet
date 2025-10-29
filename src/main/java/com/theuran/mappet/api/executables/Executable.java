package com.theuran.mappet.api.executables;

public class Executable {
    private int ticks;
    private final Runnable runnable;

    public Executable(int ticks, Runnable runnable) {
        this.ticks = ticks;
        this.runnable = runnable;
    }

    public void run() {
        this.runnable.run();
    }

    public int getTicks() {
        return this.ticks;
    }

    public void removeTick() {
        this.ticks -= 1;
    }
}
