package com.theuran.mappet.api.executables;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecutableManager {
    private ConcurrentLinkedQueue<Executable> executables = new ConcurrentLinkedQueue<>();

    public ExecutableManager() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (Executable executable : this.executables) {
                if (executable.getTicks() != 0) {
                    executable.removeTick();
                } else {
                    executable.run();

                    this.executables.remove(executable);
                }
            }
        });
    }

    public void addExecutable(int ticks, Runnable runnable) {
        this.executables.add(new Executable(ticks, runnable));
    }
}