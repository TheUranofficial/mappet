package com.theuran.mappet.client.ui.utils;

import com.theuran.mappet.Mappet;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.ui.utils.icons.Icon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MappetIcons {
    public static final Map<String, Icon> ICONS = new HashMap<>();
    public static final Link ATLAS = Mappet.link("textures/icons.png");



    public static Icon register(String id, int x, int y) {
        Icon icon = new Icon(ATLAS, id, x * 16, y * 16);

        if (ICONS.containsKey(id)) {
            try {
                throw new IllegalStateException("[Icons] Icon " + icon.id + " was already registered prior...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ICONS.put(id, icon);
        }

        return icon;
    }
}
