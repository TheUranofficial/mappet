package com.theuran.mappet.event;

import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface TriggerBlockEntityUpdateCallback {
    Event<TriggerBlockEntityUpdateCallback> EVENT = EventFactory.createArrayBacked(TriggerBlockEntityUpdateCallback.class, (listeners) -> (entity) -> {
        for(TriggerBlockEntityUpdateCallback listener : listeners) {
            listener.update(entity);
        }

    });

    void update(TriggerBlockEntity blockEntity);
}
