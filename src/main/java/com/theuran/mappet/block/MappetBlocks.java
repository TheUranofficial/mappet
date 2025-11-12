package com.theuran.mappet.block;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.block.blocks.TriggerBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MappetBlocks {
    public static final Block TRIGGER_BLOCK = register(new TriggerBlock(), "trigger", true);

    public static void init() {}

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Mappet.id(name);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }
}
