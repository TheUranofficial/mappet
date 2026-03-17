package com.theuran.mappet.client.api.triggerBlocks;

import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.event.TriggerBlockEntityUpdateCallback;
import mchorse.bbs_mod.blocks.entities.ModelBlockEntity;
import mchorse.bbs_mod.events.ModelBlockEntityUpdateCallback;

import java.util.HashSet;
import java.util.Set;

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
