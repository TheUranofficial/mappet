package com.theuran.mappet.api.keybinds;

import org.lwjgl.glfw.GLFW;

public class Keybind {
    private String name;
    private String categoryName;
    private int keycode;
    private Type type;
    private Modificator mod;

    public Keybind(String name, String categoryName, int keycode, Type type, Modificator mod) {
        this.name = name;
        this.categoryName = categoryName;
        this.type = type;
        this.keycode = keycode;
        this.mod = mod;
    }

    public String getName() {
        return this.name;
    }

    public String getCategoryName() {
        return this.categoryName;
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
