package com.theuran.mappet.client.ui;

import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIMappetKeys {
    public static final IKey SERVER_TITLE = L10n.lang("mappet.server.title");

    public static final IKey STATES_TITLE = L10n.lang("mappet.states.title");
    public static final IKey STATES_ADD = L10n.lang("mappet.states.add");
    public static final IKey STATES_REFRESH = L10n.lang("mappet.states.refresh");
    public static final IKey STATES_SEARCH = L10n.lang("mappet.states.search");

    public static final IKey HUD_SCENE_TITLE = L10n.lang("mappet.hud.scene.title");
    public static final IKey HUD_SCENE_ORTHO = L10n.lang("mappet.hud.scene.ortho");

    public static final IKey SCRIPTS_TITLE = L10n.lang("mappet.scripts.title");
    public static final IKey SCRIPTS_RUN = L10n.lang("mappet.scripts.run");
    public static final IKey SCRIPTS_SIDE = L10n.lang("mappet.scripts.side");

    public static final IKey LOGGER_TITLE = L10n.lang("mappet.logger.title");

    public static final IKey UI_BUILDER_TITLE = L10n.lang("mappet.ui.builder.title");
    public static final IKey UI_BUILDER_ELEMENT_PANEL = L10n.lang("mappet.ui.builder.element.panel.title");
    public static final IKey UI_BUILDER_ELEMENT_ADD = L10n.lang("mappet.ui.builder.element.add.tooltip");
    public static final IKey UI_BUILDER_SCRIPT = L10n.lang("mappet.ui.script.tooltip");


    public static final IKey UI_TRIGGER_BLOCK_TITLE = L10n.lang("mappet.ui.trigger_block.title");
}
