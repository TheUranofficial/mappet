package com.theuran.mappet.utils.keys;

public class Key {
    public Type type;
    public int keycode;

    public Key(Type type, int keycode) {
        this.type = type;
        this.keycode = keycode;
    }

    public enum Type {
        RELEASED, PRESSED, REPEATED
    }
}
