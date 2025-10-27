package com.theuran.mappet.network.basic;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.utils.IByteBufSerializable;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class AbstractPacket implements IByteBufSerializable {
    public final PacketByteBuf buf = PacketByteBufs.create();

    public Identifier getId() {
        return Mappet.id(this.getClass().getSimpleName().toLowerCase());
    }
}
