package com.theuran.mappet.network.packets.scripts;

import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ScriptsSendPacket extends ClientPacket {
    private List<Script> scripts = new ArrayList<>();

    public ScriptsSendPacket() {
        super();
    }

    public ScriptsSendPacket(List<Script> scripts) {
        this();
        this.scripts.addAll(scripts);
    }

    public ScriptsSendPacket(Script... scripts) {
        this();
        this.scripts.addAll(List.of(scripts));
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeCollection(this.scripts, MappetByteBuffer::writeScript);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.scripts = buf.readList(MappetByteBuffer::readScript);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        MappetClient.getScripts().setScripts(this.scripts);
    }
}
