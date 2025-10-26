package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.states.IStatesProvider;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

public class StateTrigger extends Trigger {
    private String key;
    private BaseType baseType;
    @Override
    public void execute(ScriptEvent scriptEvent) {
        Entity entity = scriptEvent.getSubject().getMinecraftEntity();

        if (entity instanceof ServerPlayerEntity player) {
            IStatesProvider playerStates = (IStatesProvider) player;

            playerStates.getStates().set(this.key, this.baseType);
        }
    }

    @Override
    public String getId() {
        return "State";
    }

    @Override
    public void toData(MapType mapType) {
        mapType.putString("key", this.key);
        mapType.put("baseType", this.baseType);
    }

    @Override
    public void fromData(MapType entries) {
        this.key = entries.getString("key");
        this.baseType = entries.get("baseType");
    }
}
