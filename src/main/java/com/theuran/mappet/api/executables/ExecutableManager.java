package com.theuran.mappet.api.executables;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecutableManager {
    ConcurrentLinkedQueue<Executable> executables = new ConcurrentLinkedQueue<>();

    public ExecutableManager() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (Executable executable : this.executables) {
                if (executable.getTicks() != 0) {
                    executable.removeTick();
                } else {
                    try {
                        if (!executable.getCode().isEmpty()) {
                            Mappet.getScripts().eval(executable.getCode(), executable.getScriptEvent());
                        } else if (!executable.getScriptEvent().getScript().isEmpty()) {
                            Mappet.getScripts().getScript(executable.getScript()).execute(executable.getScriptEvent());
                        }
                    } catch (JavetException ignored) {
                    }

                    this.executables.remove(executable);
                }
            }
        });
    }

    public void addExecutable(int ticks, String script, String function, ScriptEvent scriptEvent) {
        this.executables.add(new Executable(ticks, script, function, scriptEvent));
    }

    public void addExecutable(int ticks, String code, ScriptEvent scriptEvent) {
        this.executables.add(new Executable(ticks, code, scriptEvent));
    }
}