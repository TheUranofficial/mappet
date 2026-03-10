package com.theuran.mappet.api.keybinds;

import org.lwjgl.glfw.GLFW;

public record Keybind(String id, String category, int keycode, Type type, Modificator mod) {

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
