package com.theuran.mappet.utils;

import net.minecraft.nbt.NbtElement;

public interface INBTSerializable {
    NbtElement toNbt();

    void fromNbt(NbtElement nbt);
}