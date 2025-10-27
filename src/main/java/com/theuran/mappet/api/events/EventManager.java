package com.theuran.mappet.api.events;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.utils.BaseFileManager;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.ListType;
import mchorse.bbs_mod.data.types.MapType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EventManager extends BaseFileManager {
    public Map<String, List<Trigger>> events;

    public EventManager(Supplier<File> file) {
        super(file);

        this.events = new HashMap<>();

        for (EventType eventType : EventType.values()) {
            this.registerEvent(eventType);
        }

        //this.addTriggerToEvent(EventType.PLAYER_USE_BLOCK, new ScriptTrigger("lox", "main"));
    }

    public void event(EventType eventType, ScriptEvent scriptEvent) {
        List<Trigger> triggers = this.events.get(eventType.name().toLowerCase());

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
        List<Trigger> triggers = this.events.get(eventType.name().toLowerCase());

        triggers.add(trigger);
    }

    public boolean noTriggersInEvent(EventType eventType) {
        return this.events.get(eventType.name().toLowerCase()).isEmpty();
    }

    private void registerEvent(EventType eventType) {
        this.events.put(eventType.name().toLowerCase(), new ArrayList<>());
    }

    @Override
    public void toData(MapType data) {
        this.events.forEach((id, triggers) -> {
            ListType triggerList = new ListType();

            triggers.forEach(trigger -> {
                System.out.println(trigger.toData());
                triggerList.add(trigger.toData());
            });

            data.put(id, triggerList);
        });
    }

    @Override
    public void fromData(MapType data) {
        for (Map.Entry<String, BaseType> event : data) {
            List<Trigger> triggerList = new ArrayList<>();

            for (BaseType type : event.getValue().asList()) {
                Trigger trigger = Mappet.getEventTriggers().create(Mappet.link(type.asMap().getString("type")));

                trigger.fromData(type);

                triggerList.add(trigger);
            }

            this.events.put(event.getKey(), triggerList);
        }
    }
}
