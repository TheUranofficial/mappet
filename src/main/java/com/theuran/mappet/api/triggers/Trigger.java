package com.theuran.mappet.api.triggers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.panels.UITriggerPanel;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;
import mchorse.bbs_mod.settings.values.numeric.ValueInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public abstract class Trigger extends ValueGroup {
    private ValueBoolean isServer = new ValueBoolean("isServer", true);
    private ValueInt maxDelay = new ValueInt("maxDelay", 1);
    private ValueString type = new ValueString("type", this.getTriggerId());
    private int delay = 1;

    public Trigger() {
        super("");

        this.setId(this.getTriggerId());
        this.add(this.maxDelay);
        this.add(this.type);
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

    public void changeSide() {
        this.isServer.set(!this.isServer.get());
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

    @Environment(EnvType.CLIENT)
    public String asString() {
        return L10n.lang("mappet.triggers.types." + Mappet.getTriggers().getType(this).path).get();
    }

    @Environment(EnvType.CLIENT)
    public abstract UITriggerPanel<?> getPanel(UIEditorTriggersOverlayPanel overlayPanel);
}
