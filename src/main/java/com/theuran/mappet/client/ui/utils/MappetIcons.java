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
    public static final Icon API_BBS_TRANSFORM = register("api_bbs_transform", 1, 0);
    public static final Icon API_SCRIPT_BLOCK_ENTITY = register("api_script_block_entity", 2, 0);
    public static final Icon API_SCRIPT_ENTITY = register("api_script_entity", 3, 0);
    public static final Icon API_SCRIPT_BLOCK_STATE = register("api_script_block_state", 4, 0);
    public static final Icon API_SCRIPT_PLAYER = register("api_script_player", 5, 0);
    public static final Icon API_MAPPET_STATES = register("api_mappet_states", 6, 0);
    public static final Icon API_SCRIPT_NBT_COMPOUND = register("api_script_nbt_compound", 7, 0);
    public static final Icon API_SCRIPT_EVENT = register("api_script_event", 8, 0);
    public static final Icon API_SCRIPT_FACTORY = register("api_script_factory", 9, 0);
    public static final Icon API_SCRIPT_RAY_TRACE = register("api_script_ray_trace", 10, 0);
    public static final Icon API_SCRIPT_SERVER = register("api_script_server", 11, 0);
    public static final Icon API_SCRIPT_VECTOR = register("api_script_vector", 12, 0);
    public static final Icon API_SCRIPT_WORLD = register("api_script_world", 13, 0);
    public static final Icon API_UI_BUTTON = register("api_ui_button", 14, 0);
    public static final Icon API_UI_GRAPHICS = register("api_ui_graphics", 15, 0);

    public static final Icon API_UI_LABEL = register("api_ui_label", 0, 1);
    public static final Icon API_UI_ICON = register("api_ui_icon", 1, 1);
    public static final Icon API_UI_LAYOUT = register("api_ui_layout", 2, 1);
    public static final Icon API_UI_FORM = register("api_ui_form", 3, 1);
    public static final Icon API_UI_OVERLAY = register("api_ui_overlay", 4, 1);
    public static final Icon API_UI_SCRIPT_EDITOR = register("api_ui_script_editor", 5, 1);
    public static final Icon API_UI_TEXTBOX = register("api_ui_textbox", 6, 1);
    public static final Icon API_UI_TEXTAREA = register("api_ui_textarea", 7, 1);
    public static final Icon API_UI_TOGGLE = register("api_ui_toggle", 8, 1);
    public static final Icon API_UI_TRACKPAD = register("api_ui_trackpad", 9, 1);

    public static final Icon UIB_BUTTON = register("uib_button", 0, 2);
    public static final Icon UIB_TEXTURE = register("uib_texture", 1, 2);
    public static final Icon UIB_LABEL = register("uib_label", 2, 2);

    public static final Icon UI = register("ui", 0, 3);
    public static final Icon UI_BUILDER = register("ui_builder", 1, 3);
    public static final Icon UI_INSTANCE = register("ui_instance", 2, 3);

    public static final Icon CLIENT = register("client", 0, 4);
    public static final Icon EVENTS = register("events", 1, 4);
    public static final Icon KEYBINDS = register("keybinds", 2, 4);
    public static final Icon STATES = register("states", 3, 4);
    public static final Icon SERVER = register("server", 4, 4);

    public static final Icon MAPPET = register("mappet", 0, 5);
    public static final Icon CLICK = register("click", 1, 5);

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
