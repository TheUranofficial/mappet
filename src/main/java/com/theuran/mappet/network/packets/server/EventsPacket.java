package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.events.UIEventsOverlayPanel;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import mchorse.bbs_mod.l10n.keys.IKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsPacket extends AbstractPacket {
    public Map<EventType, Integer> events;

    public EventsPacket() {}

    public EventsPacket(Map<EventType, Integer> events) {
        this.events = events;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeMap(this.events, PacketByteBuf::writeEnumConstant, PacketByteBuf::writeInt);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.events = buf.readMap(packet -> packet.readEnumConstant(EventType.class), PacketByteBuf::readInt);
    }

    public static class ClientHandler implements ClientPacketHandler<EventsPacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, EventsPacket packet) {
            UIEventsOverlayPanel panel = MappetClient.getDashboard().eventsPanel;

            panel.list.clear();

            for (EventType eventType : packet.events.keySet()) {
                List<IKey> keys = new ArrayList<>();

                keys.add(eventType.getName());

                if (packet.events.get(eventType) != 0) {
                    keys.add(IKey.constant(" §7(§6" + packet.events.get(eventType) + "§7)§r"));
                }

                panel.list.add(IKey.comp(keys), eventType.name().toLowerCase());
            }

            panel.list.sort();
            panel.list.setCurrentValue(panel.latest);
        }
    }
}
