package com.theuran.mappet.item;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.block.MappetBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class MappetItemGroups {
    public static final ItemGroup MAPPET_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MappetItems.LOGO))
            .displayName(Text.translatable("mappet.itemGroup.name"))
            .entries((context, entries) -> {
                entries.add(MappetItems.HAMMER);
                entries.add(MappetBlocks.TRIGGER_BLOCK);
            })
            .build();

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, Mappet.id("itemGroup"), MAPPET_GROUP);
    }
}
