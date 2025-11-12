package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UICommandTriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UIItemTriggerPanel;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ItemTrigger extends Trigger {
    public ValueString itemId = new ValueString("itemId", "");

    public ItemTrigger() {
        this.add(this.itemId);
    }

    public ItemTrigger(String itemId) {
        this();
        this.itemId.set(itemId);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        Entity entity = scriptEvent.getSubject().getMinecraftEntity();

        if (entity instanceof ServerPlayerEntity player) {
            player.giveItemStack(Registries.ITEM.get(new Identifier(this.itemId.get())).getDefaultStack());
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
