package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.panels.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UIStateTriggerPanel;
import com.theuran.mappet.utils.ValueType;
import mchorse.bbs_mod.data.types.BaseType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

public class StateTrigger extends StringTrigger {
    public ValueType value = new ValueType("value", null);

    public StateTrigger() {
        super();
        this.add(this.value);
    }

    public StateTrigger(String key, BaseType value) {
        this();
        this.key.set(key);
        this.value.set(value);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        Entity entity = scriptEvent.getSubject().getMinecraftEntity();

        if (entity instanceof ServerPlayerEntity player) {
            IStatesProvider playerStates = (IStatesProvider) player;

            playerStates.getStates().set(this.key.get(), this.value.get());
        }
    }

    @Override
    public void execute(ClientScriptEvent scriptEvent) {

    }

    @Override
    public String getTriggerId() {
        return "state";
    }

    @Environment(EnvType.CLIENT)
    @Override
    public UITriggerPanel<?> getPanel(UIEditorTriggersOverlayPanel overlayPanel) {
        return new UIStateTriggerPanel(overlayPanel, this);
    }
}
