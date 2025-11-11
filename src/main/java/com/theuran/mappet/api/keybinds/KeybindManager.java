package com.theuran.mappet.api.keybinds;

import com.theuran.mappet.Mappet;
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

public class KeybindManager extends BaseFileManager {
    public Map<Keybind, List<Trigger>> keybinds;

    public KeybindManager(Supplier<File> file) {
        super(file);

        this.keybinds = new HashMap<>();
    }

    public List<Trigger> getTriggers(String name) {
        for (Map.Entry<Keybind, List<Trigger>> entry : this.keybinds.entrySet()) {
            if (entry.getKey().getId().equals(name)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void toData(MapType mapType) {
        this.keybinds.forEach((keybind, triggers) -> {
            MapType keybindMapType = new MapType();
            keybindMapType.putString("category", keybind.getCategory());
            keybindMapType.putInt("keycode", keybind.getKeycode());
            keybindMapType.putString("type", keybind.getType().name());
            keybindMapType.putString("mod", keybind.getMod().name());

            ListType triggerList = new ListType();

            triggers.forEach(trigger -> {
                triggerList.add(trigger.toData());
            });

            keybindMapType.put("triggers", triggerList);

            mapType.put(keybind.getId(), keybindMapType);
        });
    }

    @Override
    public void fromData(MapType data) {
        for (Map.Entry<String, BaseType> event : data) {
            MapType value = (MapType) event.getValue();
            Keybind keybind = new Keybind(
                    event.getKey(),
                    value.getString("category"),
                    value.getInt("keycode"),
                    Keybind.Type.valueOf(value.getString("type")),
                    Keybind.Modificator.valueOf(value.getString("mod"))
            );

            List<Trigger> triggerList = new ArrayList<>();

            for (BaseType type : event.getValue().asList()) {
                Trigger trigger = Mappet.getTriggers().create(Mappet.link(type.asMap().getString("type")));

                trigger.fromData(type);

                triggerList.add(trigger);
            }

            this.keybinds.put(keybind, triggerList);
        }
    }
}