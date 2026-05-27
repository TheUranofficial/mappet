package com.theuran.mappet.client.api.triggerBlocks;

import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.event.TriggerBlockEntityUpdateCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class ClientTriggerBlocksManager {
    public static final Set<TriggerBlockEntity> capturedTriggerBlocks = new HashSet<>();

    public ClientTriggerBlocksManager() {
        TriggerBlockEntityUpdateCallback.EVENT.register((entity) -> {
            if (entity.getWorld().isClient()) {
                capturedTriggerBlocks.add(entity);
            }
        });
    }
}
