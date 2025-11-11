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

    public static final Icon API_BBS_FORM = register("api_bbs_form", 0, 0);
    public static final Icon API_BBS_TRANSFORM = register("api_bbs_transform", 16, 0);
    public static final Icon API_SCRIPT_BLOCK_ENTITY = register("api_script_block_entity", 32, 0);
    public static final Icon API_SCRIPT_ENTITY = register("api_script_entity", 48, 0);
    public static final Icon API_SCRIPT_BLOCK_STATE = register("api_script_entity", 64, 0);
    public static final Icon API_SCRIPT_PLAYER = register("api_script_entity", 80, 0);
    public static final Icon API_MAPPET_STATES = register("api_mappet_states", 96, 0);
    public static final Icon API_SCRIPT_NBT_COMPOUND = register("api_script_nbt_compound", 112, 0);
    public static final Icon API_SCRIPT_EVENT = register("api_script_event", 128, 0);
    public static final Icon API_SCRIPT_FACTORY = register("api_script_factory", 144, 0);
    public static final Icon API_SCRIPT_RAY_TRACE = register("api_script_ray_trace", 160, 0);
    public static final Icon API_SCRIPT_SERVER = register("api_script_server", 176, 0);
    public static final Icon API_SCRIPT_VECTOR = register("api_script_server", 192, 0);
    public static final Icon API_SCRIPT_WORLD = register("api_script_world", 208, 0);
    public static final Icon API_UI_BUTTON = register("api_ui_button", 224, 0);
    public static final Icon API_UI_GRAPHICS = register("api_ui_graphics", 240, 0);

    public static final Icon API_UI_LABEL = register("api_ui_label", 0, 16);
    public static final Icon API_UI_ICON = register("api_ui_icon", 16, 16);
    public static final Icon API_UI_LAYOUT = register("api_ui_layout", 32, 16);
    public static final Icon API_UI_FORM = register("api_ui_form", 48, 16);
    public static final Icon API_UI_OVERLAY = register("api_ui_overlay", 64, 16);
    public static final Icon API_UI_SCRIPT_EDITOR = register("api_ui_script_editor", 80, 16);
    public static final Icon API_UI_TEXTBOX = register("api_ui_textbox", 96, 16);
    public static final Icon API_UI_TEXTAREA = register("api_ui_textarea", 112, 16);
    public static final Icon API_UI_TOGGLE = register("api_ui_toggle", 128, 16);
    public static final Icon API_UI_TRACKPAD = register("api_ui_toggle", 144, 16);


    public static final Icon UI = register("ui", 0, 48);
    public static final Icon UI_BUILDER = register("ui_builder", 16, 48);
    public static final Icon UI_INSTANCE = register("ui_instance", 32, 48);

    public static final Icon CLIENT = register("client", 0, 64);
    public static final Icon EVENTS = register("events", 16, 64);
    public static final Icon KEYBINDS = register("keybinds", 32, 64);
    public static final Icon STATES = register("states", 48, 64);
    public static final Icon SERVER = register("states", 48, 64);

    public static final Icon MAPPET = register("mappet", 0, 80);
    public static final Icon CLICK = register("click", 16, 80);

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
