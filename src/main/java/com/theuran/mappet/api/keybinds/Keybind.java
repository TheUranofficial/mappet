package com.theuran.mappet.api.keybinds;

import org.lwjgl.glfw.GLFW;

public class Keybind {
    private final String id;
    private final String category;
    private final int keycode;
    private final Type type;
    private final Modificator mod;

    public Keybind(String id, String category, int keycode, Type type, Modificator mod) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.keycode = keycode;
        this.mod = mod;
    }

    public String getId() {
        return this.id;
    }

    public String getCategory() {
        return this.category;
    }

    public int getKeycode() {
        return this.keycode;
    }

    public Type getType() {
        return this.type;
    }

    public Modificator getMod() {
        return this.mod;
    }

    public enum Type {
        RELEASED, PRESSED
    }

    public enum Modificator {
        CTRL(GLFW.GLFW_KEY_LEFT_CONTROL),
        SHIFT(GLFW.GLFW_KEY_LEFT_SHIFT),
        ALT(GLFW.GLFW_KEY_LEFT_ALT),
        NONE(-1);

        private final int keycode;

        Modificator(int keycode) {
            this.keycode = keycode;
        }

        public int getKeycode() {
            return this.keycode;
        }
    }
}
