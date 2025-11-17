package com.theuran.mappet.network.packets.events;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.utils.MappetByteBuffer;
import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ClientPacketHandler;
import com.theuran.mappet.network.core.ServerPacketHandler;
import com.theuran.mappet.utils.ValueEnum;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.MapType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class EventsExecuteTriggersPacket extends AbstractPacket implements ClientPacketHandler, ServerPacketHandler {
    public ValueEnum<EventType> eventType = new ValueEnum<>("eventType", null);
    public Trigger trigger;

    public ScriptEvent scriptEvent;
    public ClientScriptEvent clientScriptEvent;

    public EventsExecuteTriggersPacket() {
        super();
        this.add(this.eventType);
    }

    public EventsExecuteTriggersPacket(EventType eventType, Trigger trigger, ScriptEvent scriptEvent) {
        this();
        this.eventType.set(eventType);
        this.trigger = trigger;
        this.scriptEvent = scriptEvent;
    }

    public EventsExecuteTriggersPacket(EventType eventType, Trigger trigger, ClientScriptEvent clientScriptEvent) {
        this();
        this.eventType.set(eventType);
        this.trigger = trigger;
        this.clientScriptEvent = clientScriptEvent;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        super.toBytes(buf);

        DataStorageUtils.writeToPacket(buf, this.trigger.toData());

        if (this.scriptEvent != null) {
            buf.writeBoolean(true);
            MappetByteBuffer.writeScriptEvent(buf, this.scriptEvent);
        } else {
            buf.writeBoolean(false);
            MappetByteBuffer.writeClientScriptEvent(buf, this.clientScriptEvent);
        }
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        super.fromBytes(buf);

        MapType data = DataStorageUtils.readFromPacket(buf).asMap();
        this.trigger = Mappet.getTriggers().create(Mappet.link(data.getString("type")));

        this.trigger.fromData(data);

        if (buf.readBoolean()) {
            this.scriptEvent = MappetByteBuffer.readScriptEvent(buf);
        } else {
            this.clientScriptEvent = MappetByteBuffer.readClientScriptEvent(buf);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        Mappet.getEvents().eventClient(this.eventType.get(), this.clientScriptEvent);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        ScriptEvent scriptEvent = ScriptEvent.create(
                this.scriptEvent.getScript(),
                this.scriptEvent.getFunction(),
                player.getWorld().getEntityById((Integer) this.scriptEvent.getValue("__subjectId")),
                player.getWorld().getEntityById((Integer) this.scriptEvent.getValue("__objectId")),
                (ServerWorld) player.getWorld(),
                server
        );

        scriptEvent.setValues(this.scriptEvent.getValues());

        Mappet.getEvents().eventServer(this.eventType.get(), scriptEvent);
    }
}
