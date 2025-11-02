package com.theuran.mappet.api.events;

import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;

public enum EventType {
    ENTITY_DEATH,
    ENTITY_DAMAGE,

    SERVER_TICK,
    SERVER_STARTED,
    SERVER_STOPPED,

    CLIENT_TICK,
    CLIENT_ATTACK_BLOCK,
    CLIENT_ATTACK_ENTITY,
    CLIENT_USE_BLOCK,
    CLIENT_USE_ENTITY,
    CLIENT_USE_ITEM,

    PLAYER_ATTACK_BLOCK,
    PLAYER_ATTACK_ENTITY,
    PLAYER_USE_BLOCK,
    PLAYER_USE_ENTITY,
    PLAYER_USE_ITEM,
    PLAYER_JOIN,
    PLAYER_DISCONNECT,
    PLAYER_CHAT_MESSAGE;

    public IKey getName() {
        return L10n.lang("mappet.events.name." + this.name().toLowerCase().replace("_", "."));
    }

    public IKey getDescription() {
        return L10n.lang("mappet.events.description." + this.name().toLowerCase().replace("_", "."));
    }

    public IKey getVariables() {
        return L10n.lang("mappet.events.variables." + this.name().toLowerCase().replace("_", "."));
    }
}
