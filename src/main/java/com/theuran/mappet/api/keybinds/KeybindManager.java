package com.theuran.mappet.api.keybinds;

import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.utils.BaseFileManager;
import com.theuran.mappet.utils.keys.Key;
import com.theuran.mappet.utils.keys.Keybind;
import mchorse.bbs_mod.data.types.MapType;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class KeybindManager extends BaseFileManager {
    Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public KeybindManager(Supplier<File> file) {
        super(file);

        keybinds.put(new Keybind(new Key(Key.Type.PRESSED, GLFW.GLFW_KEY_E)), new ArrayList<>());
    }

    public Map<Keybind, List<Trigger>> getKeybinds() {
        return this.keybinds;
    }

    @Override
    public void toData(MapType mapType) {
    }

    @Override
    public void fromData(MapType entries) {

    }
}