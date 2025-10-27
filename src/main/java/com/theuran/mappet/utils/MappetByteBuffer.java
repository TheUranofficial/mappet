package com.theuran.mappet.utils;

import com.theuran.mappet.api.scripts.Script;
import net.minecraft.network.PacketByteBuf;

public class MappetByteBuffer {
    public static void writeScript(PacketByteBuf buf, Script script) {
        buf.writeString(script.getId());
        buf.writeString(script.getContent());
        buf.writeBoolean(script.isServer());
    }

    public static Script readScript(PacketByteBuf buf) {
        String id = buf.readString();
        String content = buf.readString();
        boolean server = buf.readBoolean();

        Script script = new Script();

        script.setId(id);
        script.setContent(content);
        script.setServer(server);

        return script;
    }
}
