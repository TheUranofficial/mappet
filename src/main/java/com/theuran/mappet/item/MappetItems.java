package com.theuran.mappet.item;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.item.items.HammerItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MappetItems {
    public static Item LOGO = register(new Item(new FabricItemSettings()), "logo");
    public static Item HAMMER = register(new HammerItem(), "hammer");

    public static void init() {

    }

    public static Item register(Item item, String id) {
        return Registry.register(Registries.ITEM, Mappet.id(id), item);
    }
}
