package com.theuran.mappet.network.packets.events;

import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.events.UIEventsOverlayPanel;
import com.theuran.mappet.network.core.ClientPacket;
import mchorse.bbs_mod.l10n.keys.IKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsUpdatePacket extends ClientPacket {
    private Map<EventType, Integer> events;

    public EventsUpdatePacket() {
        super();
    }

    public EventsUpdatePacket(Map<EventType, Integer> events) {
        this();
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

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        UIEventsOverlayPanel panel = MappetClient.getDashboard().eventsPanel;

        panel.list.clear();

        for (EventType eventType : this.events.keySet()) {
            List<IKey> keys = new ArrayList<>();

            keys.add(eventType.getName());

            if (this.events.get(eventType) != 0) {
                keys.add(IKey.constant(" §7(§6" + this.events.get(eventType) + "§7)§r"));
            }

            panel.list.add(IKey.comp(keys), eventType.name().toLowerCase());
        }

        panel.list.sort();
        panel.list.setCurrentValue(panel.latest);
    }
}
