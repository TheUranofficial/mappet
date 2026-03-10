package com.theuran.mappet.api.triggers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.events.EventsRequestPacket;
import com.theuran.mappet.network.packets.keybinds.KeybindsRequestPacket;

import java.util.ArrayList;
import java.util.List;

public enum RequestTrigger {
    EVENTS,
    KEYBINDS;

    public List<Trigger> getTriggers(String value) {
        switch (this) {
            case EVENTS -> {
                return Mappet.getEvents().getTriggers(EventType.valueOf(value.toUpperCase()));
            }
            case KEYBINDS -> {
                return Mappet.getKeybinds().getTriggers(value);
            }
        }

        return new ArrayList<>();
    }

    public void setTriggers(String value, List<Trigger> triggers) {
        switch (this) {
            case EVENTS -> Mappet.getEvents().events.put(EventType.valueOf(value.toUpperCase()), triggers);
            case KEYBINDS -> Mappet.getKeybinds().keybinds.put(Mappet.getKeybinds().getKeybind(value), triggers);
        }
    }

    public void clientSetTriggers(String value, List<Trigger> triggers) {
        switch (this) {
            case EVENTS -> {
                Mappet.getEvents().events.put(EventType.valueOf(value.toUpperCase()), triggers);

                if (MappetClient.getDashboard().eventsPanel.panel != null) {
                    MappetClient.getDashboard().eventsPanel.panel.set(this, value, triggers);
                }
            }
            case KEYBINDS -> {
                Mappet.getKeybinds().keybinds.put(Mappet.getKeybinds().getKeybind(value), triggers);

                if (MappetClient.getDashboard().keybindsPanel.panel != null) {
                    MappetClient.getDashboard().keybindsPanel.panel.panel.set(this, value, triggers);
                }
            }
        }
    }

    public void update() {
        switch (this) {
            case EVENTS -> Dispatcher.sendToServer(new EventsRequestPacket());
            case KEYBINDS -> Dispatcher.sendToServer(new KeybindsRequestPacket());
        }
    }
}
