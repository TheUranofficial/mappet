package com.theuran.mappet.network.packets.keybinds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.ServerPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class KeybindsSetPacket extends ServerPacket {
    private final ValueString id = new ValueString("id", "");
    private Keybind keybind;

    public KeybindsSetPacket() {
        super();
        this.add(this.id);
    }

    public KeybindsSetPacket(String id, Keybind keybind) {
        this();
        this.id.set(id);
        this.keybind = keybind;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        super.toBytes(buf);
        MappetByteBuffer.writeKeybind(buf, this.keybind);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        super.fromBytes(buf);
        this.keybind = MappetByteBuffer.readKeybind(buf);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Mappet.getKeybinds().setKeybind(this.id.get(), this.keybind);

        Dispatcher.sendToAll(new KeybindsSyncPacket(Mappet.getKeybinds().keybinds), server);
    }
}
