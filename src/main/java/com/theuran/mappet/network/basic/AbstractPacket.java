package com.theuran.mappet.network.basic;

import com.theuran.mappet.Mappet;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class AbstractPacket {
    public final PacketByteBuf buf = PacketByteBufs.create();

    public abstract void toBytes(PacketByteBuf buf);

    public abstract void fromBytes(PacketByteBuf buf);

    public Identifier getId() {
        return Mappet.id(this.getClass().getSimpleName().toLowerCase());
    }
}
