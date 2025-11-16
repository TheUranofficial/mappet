package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.panels.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UIItemTriggerPanel;
import mchorse.bbs_mod.settings.values.mc.ValueItemStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ItemTrigger extends StringTrigger {
    public ValueItemStack stack = new ValueItemStack("stack");

    public ItemTrigger() {
        super();
        this.add(this.stack);
    }

    public ItemTrigger(ItemStack stack) {
        this();
        this.stack.set(stack);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        Entity entity = scriptEvent.getSubject().getMinecraftEntity();

        if (entity instanceof ServerPlayerEntity player) {
            player.giveItemStack(this.stack.get());
        }
    }

    @Override
    public void execute(ClientScriptEvent scriptEvent) {

    }

    @Override
    public String getTriggerId() {
        return "item";
    }

    @Environment(EnvType.CLIENT)
    @Override
    public UITriggerPanel<?> getPanel(UIEditorTriggersOverlayPanel overlayPanel) {
        return new UIItemTriggerPanel(overlayPanel, this);
    }
}
