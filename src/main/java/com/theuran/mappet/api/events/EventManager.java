package com.theuran.mappet.api.events;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.triggers.ScriptTrigger;
import com.theuran.mappet.api.triggers.Trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private final Map<EventType, List<Trigger>> events;

    public EventManager() {
        this.events = new HashMap<>();

        for (EventType eventType : EventType.values()) {
            this.registerEvent(eventType);
        }

        this.addTriggerToEvent(EventType.PLAYER_USE_BLOCK, new ScriptTrigger("lox", "main"));
    }

    public void event(EventType eventType, ScriptEvent scriptEvent) {
        List<Trigger> triggers = this.events.get(eventType);

        for (Trigger trigger : triggers) {
            if (trigger.getDelay() == trigger.getMaxDelay()) {
                trigger.execute(scriptEvent);
                trigger.resetDelay();
            } else {
                trigger.delay();
            }
        }
    }

    public void addTriggerToEvent(EventType eventType, Trigger trigger) {
        List<Trigger> triggers = this.events.get(eventType);

        triggers.add(trigger);
    }

    public boolean noTriggersInEvent(EventType eventType) {
        return this.events.get(eventType).isEmpty();
    }

    private void registerEvent(EventType eventType) {
        this.events.put(eventType, new ArrayList<>());
    }
}
