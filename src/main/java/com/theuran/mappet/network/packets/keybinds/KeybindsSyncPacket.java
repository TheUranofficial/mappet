package com.theuran.mappet.network.packets.keybinds;

import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import net.minecraft.network.PacketByteBuf;

import java.util.List;
import java.util.Map;

public class KeybindsSyncPacket extends ClientPacket {
    Map<Keybind, List<Trigger>> keybinds;

    public KeybindsSyncPacket() {
        super();
    }

    public KeybindsSyncPacket(Map<Keybind, List<Trigger>> keybinds) {
        this.keybinds = keybinds;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeMap(this.keybinds, MappetByteBuffer::writeKeybind, MappetByteBuffer::writeTriggerList);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.keybinds = buf.readMap(MappetByteBuffer::readKeybind, MappetByteBuffer::readTriggerList);
    }

    @Override
    public void handleClient() {
        MappetClient.getKeybinds().keybinds.clear();
        MappetClient.getKeybinds().keybinds.putAll(this.keybinds);
    }
}
