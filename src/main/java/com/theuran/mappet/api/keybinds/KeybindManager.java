package com.theuran.mappet.api.keybinds;

import com.theuran.mappet.api.triggers.ScriptTrigger;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.utils.BaseFileManager;
import mchorse.bbs_mod.data.types.MapType;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class KeybindManager extends BaseFileManager {
    public Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public KeybindManager(Supplier<File> file) {
        super(file);

        List<Trigger> triggers = new ArrayList<>();

        ScriptTrigger scriptTrigger = new ScriptTrigger("f", "main");

        triggers.add(scriptTrigger);

        this.keybinds.put(new Keybind("lox", "mappet", GLFW.GLFW_KEY_G, Keybind.Type.RELEASED, Keybind.Modificator.NONE), triggers);
    }

    public List<Trigger> getTriggers(String name) {
        for (Map.Entry<Keybind, List<Trigger>> entry : this.keybinds.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void toData(MapType mapType) {
    }

    @Override
    public void fromData(MapType entries) {

    }
}