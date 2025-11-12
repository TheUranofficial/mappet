package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UICommandTriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UIStateTriggerPanel;
import com.theuran.mappet.utils.ValueType;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

public class StateTrigger extends Trigger {
    public ValueString key = new ValueString("key", "");
    public ValueType baseType = new ValueType("baseType", null);

    public StateTrigger() {
        this.add(this.key);
        this.add(this.baseType);
    }

    public StateTrigger(String key, BaseType baseType) {
        this();
        this.key.set(key);
        this.baseType.set(baseType);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        Entity entity = scriptEvent.getSubject().getMinecraftEntity();

        if (entity instanceof ServerPlayerEntity player) {
            IStatesProvider playerStates = (IStatesProvider) player;

            playerStates.getStates().set(this.key.get(), this.baseType.get());
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
