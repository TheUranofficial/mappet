package com.theuran.mappet.client.ui;

import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIMappetKeys {
    public static final IKey SERVER_SETTINGS_TITLE = L10n.lang("mappet.server.settings.title");
    public static final IKey SERVER_SETTINGS_STATES_TITLE = L10n.lang("mappet.server.settings.states.title");
    public static final IKey SERVER_SETTINGS_STATES_PLAYER_TITLE = L10n.lang("mappet.server.settings.states.player.title");
    public static final IKey SERVER_SETTINGS_STATES_PICK = L10n.lang("mappet.server.settings.states.pick");
    public static final IKey HUD_SCENE_TITLE = L10n.lang("mappet.hud.scene.title");
    public static final IKey HUD_SCENE_ORTHO = L10n.lang("mappet.hud.scene.ortho");
    public static final IKey SCRIPTS_TITLE = L10n.lang("mappet.scripts.title");
    public static final IKey LOGGER_TITLE = L10n.lang("mappet.logger.title");
    public static final IKey SCRIPTS_RUN = L10n.lang("mappet.scripts.run");
}
