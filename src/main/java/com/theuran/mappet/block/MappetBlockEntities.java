package com.theuran.mappet.block;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MappetBlockEntities {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Mappet.id(path), blockEntityType);
    }

    public static final BlockEntityType<TriggerBlockEntity> TRIGGER_BLOCK = register(
            "trigger",
            BlockEntityType.Builder.create(TriggerBlockEntity::new, MappetBlocks.TRIGGER_BLOCK).build()
    );

    public static void init() {
    }
}
