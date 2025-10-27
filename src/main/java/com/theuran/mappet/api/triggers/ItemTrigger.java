package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ItemTrigger extends Trigger {
    String itemId;
    @Override
    public void execute(ScriptEvent scriptEvent) {
        Entity entity = scriptEvent.getSubject().getMinecraftEntity();

        if (entity instanceof ServerPlayerEntity player) {
            player.giveItemStack(Registries.ITEM.get(new Identifier(this.itemId)).getDefaultStack());
        }
    }

    @Override
    public String getId() {
        return "Item";
    }

    @Override
    public void toData(MapType mapType) {
        mapType.putString("itemId", this.itemId);
    }

    @Override
    public void fromData(MapType entries) {
        this.itemId = entries.getString("itemId");
    }
}
