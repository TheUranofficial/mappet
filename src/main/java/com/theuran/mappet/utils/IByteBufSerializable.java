package com.theuran.mappet.utils;

import net.minecraft.network.PacketByteBuf;

public interface IByteBufSerializable {
    void toBytes(PacketByteBuf buf);

    void fromBytes(PacketByteBuf buf);
}
