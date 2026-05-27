package com.theuran.mappet.api.triggerBlocks;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TriggerBlocksManager {
    private List<TriggerBlockUpdater> listeners = new ArrayList<>();

    public TriggerBlocksManager() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (TriggerBlocksManager.TriggerBlockUpdater updater : Mappet.getTriggerBlocks().getListeners()) {
                ServerWorld world = server.getWorld(World.OVERWORLD);

                if (!world.isChunkLoaded(updater.pos)) {
                    world.setChunkForced(updater.pos.getX(), updater.pos.getY(), true);

                    BlockEntity be = world.getBlockEntity(updater.pos);

                    if (be instanceof TriggerBlockEntity triggerBlockEntity) {
                        BlockState oldState = world.getBlockState(updater.pos);

                        if (updater.triggersRMB != null) {
                            triggerBlockEntity.getTriggersRMB().clear();

                            for (Trigger trigger : updater.triggersRMB) {
                                triggerBlockEntity.getTriggersRMB().add(trigger);
                            }
                        }

                        if (updater.triggersLMB != null) {
                            triggerBlockEntity.getTriggersLMB().clear();

                            for (Trigger trigger : updater.triggersLMB) {
                                triggerBlockEntity.getTriggersLMB().add(trigger);
                            }
                        }

                        BlockState newState = world.getBlockState(updater.pos);

                        world.updateListeners(updater.pos, oldState, newState, 0);
                    }
                }
            }

            Mappet.getTriggerBlocks().getListeners().clear();
        });
    }

    public void addListener(TriggerBlockUpdater updater) {
        this.listeners.add(updater);
    }

    public List<TriggerBlockUpdater> getListeners() {
        return this.listeners;
    }

    public static class TriggerBlockUpdater {
        public BlockPos pos;
        public List<Trigger> triggersRMB;
        public List<Trigger> triggersLMB;

        public TriggerBlockUpdater(BlockPos pos, List<Trigger> triggersRMB, List<Trigger> triggersLMB) {
            this.pos = pos;
            this.triggersRMB = triggersRMB;
            this.triggersLMB = triggersLMB;
        }
    }
}
