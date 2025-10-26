package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.data.IMapSerializable;

public abstract class Trigger implements IMapSerializable {
    private int maxDelay = 1;
    private int delay = 1;

    public abstract void execute(ScriptEvent scriptEvent);

    public abstract String getId();

    public int getMaxDelay() {
        return this.maxDelay;
    }

    public int getDelay() {
        return this.delay;
    }

    public void resetDelay() {
        this.delay = 1;
    }

    public Trigger maxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
        return this;
    }

    public void delay() {
        this.delay += 1;
    }
}
