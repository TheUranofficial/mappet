package com.theuran.mappet.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class InputUtils {
    private static final Map<Integer, Boolean> keyStates = new HashMap<>();
    private static final Map<Integer, Boolean> previousKeyStates = new HashMap<>();

    // Диапазоны валидных клавиш GLFW
    private static final int MIN_KEY = GLFW.GLFW_KEY_SPACE;
    private static final int MAX_KEY = GLFW.GLFW_KEY_LAST;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Сохраняем предыдущие состояния
            previousKeyStates.clear();
            previousKeyStates.putAll(keyStates);

            // Очищаем текущие состояния
            keyStates.clear();

            // Обновляем только валидные клавиши
            updateKeyStates();
        });
    }

    private static void updateKeyStates() {
        long window = getWindow();
        if (window == 0) return;

        // Проверяем только клавиши в валидном диапазоне
        for (int keyCode = MIN_KEY; keyCode <= MAX_KEY; keyCode++) {
            keyStates.put(keyCode, InputUtil.isKeyPressed(window, keyCode));
        }
    }

    /**
     * Проверяет, была ли клавиша отпущена в текущем тике
     */
    public static boolean isKeyReleased(int keyCode) {
        if (!keyStates.containsKey(keyCode) || !previousKeyStates.containsKey(keyCode)) {
            return false;
        }

        return previousKeyStates.get(keyCode) && !keyStates.get(keyCode);
    }

    /**
     * Проверяет, нажата ли клавиша в текущем тике
     */
    public static boolean isKeyPressed(int keyCode) {
        long window = getWindow();
        if (window == 0) return false;

        return InputUtil.isKeyPressed(window, keyCode);
    }

    /**
     * Проверяет, была ли клавиша только что нажата (в этом тике)
     */
    public static boolean wasKeyJustPressed(int keyCode) {
        if (!keyStates.containsKey(keyCode) || !previousKeyStates.containsKey(keyCode)) {
            return isKeyPressed(keyCode);
        }

        return !previousKeyStates.get(keyCode) && keyStates.get(keyCode);
    }

    /**
     * Вспомогательные методы для проверки конкретных клавиш
     */
    public static boolean isKeyPressed(InputUtil.Key key) {
        return isKeyPressed(key.getCode());
    }

    public static boolean isKeyReleased(InputUtil.Key key) {
        return isKeyReleased(key.getCode());
    }

    public static boolean wasKeyJustPressed(InputUtil.Key key) {
        return wasKeyJustPressed(key.getCode());
    }

    private static long getWindow() {
        return net.minecraft.client.MinecraftClient.getInstance().getWindow().getHandle();
    }
}
