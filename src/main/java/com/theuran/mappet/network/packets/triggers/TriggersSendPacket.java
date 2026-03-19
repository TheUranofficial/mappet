package com.theuran.mappet.network.packets.triggers;

import com.theuran.mappet.api.triggers.RequestTrigger;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.CommonPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import com.theuran.mappet.utils.values.ValueRequestTrigger;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class TriggersSendPacket extends CommonPacket {
    private final ValueRequestTrigger type = new ValueRequestTrigger("requestTrigger", null);
    private final ValueString id = new ValueString("id", "");
    private List<Trigger> triggers;

    public TriggersSendPacket() {
        super();
        this.add(this.type);
        this.add(this.id);
    }

    public TriggersSendPacket(RequestTrigger type, String id, List<Trigger> triggers) {
        this();
        this.type.set(type);
        this.id.set(id);
        this.triggers = triggers;
    }

    public TriggersSendPacket(RequestTrigger type, String id) {
        this();
        this.type.set(type);
        this.id.set(id);
        this.triggers = this.type.get().getTriggers(id);
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        super.toBytes(buf);

        MappetByteBuffer.writeTriggerList(buf, this.triggers);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        super.fromBytes(buf);

        this.triggers = MappetByteBuffer.readTriggerList(buf);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        this.type.get().clientSetTriggers(this.id.get(), this.triggers);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        this.type.get().setTriggers(this.id.get(), this.triggers);

        Dispatcher.sendToAll(new TriggersSendPacket(this.type.get(), this.id.get(), this.triggers), server);
    }
}
