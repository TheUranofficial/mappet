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
    public final Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public KeybindManager(Supplier<File> file) {
        super(file);
    }

    public List<Keybind> getKeybindings() {
        return new ArrayList<>(this.keybinds.keySet());
    }

    public Keybind getKeybind(String keybindId) {
        for (Keybind keybind : this.getKeybindings()) {
            if (keybind.id().equals(keybindId)) {
                return keybind;
            }
        }
        return null;
    }

    public void addKeybind(Keybind keybind) {
        this.keybinds.put(keybind, new ArrayList<>());
    }

    public void setKeybind(String id, Keybind keybind) {
        for (Map.Entry<Keybind, List<Trigger>> entry : this.keybinds.entrySet()) {
            if (entry.getKey().id().equals(id)) {
                this.keybinds.remove(entry.getKey());
                this.keybinds.put(keybind, entry.getValue());
                return;
            }
        }

        this.addKeybind(keybind);
    }

    public List<Trigger> getTriggers(String id) {
        for (Map.Entry<Keybind, List<Trigger>> entry : this.keybinds.entrySet()) {
            if (entry.getKey().id().equals(id)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void toData(MapType mapType) {
        this.keybinds.forEach((keybind, triggers) -> {
            MapType keybindMapType = new MapType();
            keybindMapType.putString("category", keybind.category());
            keybindMapType.putInt("keycode", keybind.keycode());
            keybindMapType.putString("type", keybind.type().name());
            keybindMapType.putString("mod", keybind.mod().name());

            ListType triggerList = new ListType();

            triggers.forEach(trigger -> {
                triggerList.add(trigger.toData());
            });

            keybindMapType.put("triggers", triggerList);

            mapType.put(keybind.id(), keybindMapType);
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

            for (BaseType type : value.getList("triggers").asList()) {
                Trigger trigger = Mappet.getTriggers().create(Mappet.link(type.asMap().getString("type")));

                trigger.fromData(type);

                triggerList.add(trigger);
            }

            this.keybinds.put(keybind, triggerList);
        }
    }
}