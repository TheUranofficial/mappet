package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;
import mchorse.bbs_mod.settings.values.numeric.ValueInt;

public abstract class Trigger extends ValueGroup {
    private ValueBoolean isServer = new ValueBoolean("isServer", true);
    private ValueInt maxDelay = new ValueInt("maxDelay", 1);
    private int delay = 1;

    public Trigger() {
        super("");

        this.setId(this.getTriggerId());
        this.add(this.maxDelay);
    }

    public abstract void execute(ScriptEvent scriptEvent);

    public abstract void execute(ClientScriptEvent scriptEvent);

    public abstract String getTriggerId();

    public int getMaxDelay() {
        return this.maxDelay.get();
    }

    public int getDelay() {
        return this.delay;
    }

    public boolean isServer() {
        return this.isServer.get();
    }

    public void resetDelay() {
        this.delay = 1;
    }

    public Trigger maxDelay(int maxDelay) {
        this.maxDelay.set(maxDelay);
        return this;
    }

    public void delay() {
        this.delay += 1;
    }

    @Override
    public void fromData(BaseType data) {
        super.fromData(data);

        this.setId(data.asMap().getString("type"));
    }

    @Override
    public BaseType toData() {
        MapType data = new MapType();

        data.putString("type", this.getId());
        data.combine(super.toData().asMap());

        return data;
    }
}
