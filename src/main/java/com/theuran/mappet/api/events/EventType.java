package com.theuran.mappet.api.events;

public enum EventType {
    ENTITY_DEATH,
    ENTITY_DAMAGE,

    SERVER_TICK,
    SERVER_STARTED,
    SERVER_STOPPED,

    PLAYER_ATTACK_BLOCK,
    PLAYER_ATTACK_ENTITY,
    PLAYER_USE_BLOCK,
    PLAYER_USE_ENTITY,
    PLAYER_USE_ITEM
}
