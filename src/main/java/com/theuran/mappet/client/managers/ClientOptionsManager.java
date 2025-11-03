package com.theuran.mappet.client.managers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.utils.BooleanUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Environment(EnvType.CLIENT)
public enum ClientOptionsManager {
    INSTANCE;

    private final Pattern entryPattern = Pattern.compile("([^:]+):(.+)");
    private final Path optionsPath = MinecraftClient.getInstance().options.getOptionsFile().toPath();
    private final Map<String, Object> optionsCache = new LinkedHashMap<>();
    private final Map<String, Class<?>> optionsType = new HashMap<>();

    public Map<String, Object> map() {
        return optionsCache;
    }

    public Object get(String key) {
        return optionsCache.get(key);
    }

    public void set(String key, Object value) {
        if (!optionsCache.containsKey(key)) throw new IllegalArgumentException(
            "Unknown option: " + key
        );
        Class<?> expectedType = optionsType.get(key);
        if (!isCompatible(expectedType, value)) throw new IllegalArgumentException(
            "Type mismatch for key \"" + key + "\": expected " + expectedType.getSimpleName() + ", got " + value.getClass().getSimpleName()
        );
        optionsCache.put(key, value);
        save();
    }

    public synchronized void load() {
        optionsCache.clear();
        optionsType.clear();
        try {
            for (String line : Files.readAllLines(optionsPath)) {
                Matcher matcher = entryPattern.matcher(line);
                if (!matcher.matches()) continue;

                String key = matcher.group(1).trim();
                Object value = parseValue(matcher.group(2).trim());
                optionsCache.put(key, value);
                optionsType.put(key, value.getClass());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void save() {
        try {
            List<String> lines = new ArrayList<>();
            optionsCache.forEach((key, value) -> lines.add(key + ':' + formatValue(key, value)));
            Files.write(optionsPath, lines);
            MinecraftClient.getInstance().options.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isCompatible(Class<?> expected, Object actual) {
        return (
            (expected == Integer.class && actual instanceof Integer) ||
            (expected == Double.class && (actual instanceof Double || actual instanceof Integer)) ||
            (expected == Boolean.class && actual instanceof Boolean) ||
            (expected == String.class && actual instanceof String) ||
            (expected == List.class && actual instanceof List)
        );
    }

    private Object parseValue(String raw) {
        if (raw.startsWith("[") && raw.endsWith("]")) {
            String inner = raw.substring(1, raw.length() - 1).trim();
            if (inner.isEmpty()) return new ArrayList<>();
            String[] parts = inner.split(",");
            List<String> list = new ArrayList<>();
            for (String part : parts) list.add(stripQuotes(part.trim()));
            return list;
        }

        if (raw.startsWith("\"") && raw.endsWith("\"")) return stripQuotes(raw);
        if (BooleanUtils.isBoolean(raw)) return Boolean.parseBoolean(raw);

        try {
            if (raw.contains(".")) return Double.parseDouble(raw);
            else return Integer.parseInt(raw);
        } catch (NumberFormatException ignored) {}
        return raw;
    }

    private String formatValue(String key, Object value) {
        if (key.startsWith("key_") || value instanceof Boolean || value instanceof Number) return value.toString();
        if (value instanceof List<?> list) return (
            '[' + String.join(",", list.stream()
            .map(v -> '"' + v.toString() + '"')
            .toList()) + ']'
        );
        return '"' + value.toString() + '"';
    }

    private String stripQuotes(String s) {
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2)
            return s.substring(1, s.length() - 1);
        return s;
    }

    public void invokeEvent() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientScriptEvent scriptEvent = ClientScriptEvent.create(client.player, null, client.world);

        Mappet.getEvents().eventClient(EventType.CLIENT_CHANGE_OPTIONS, scriptEvent);
    }

    static {
        INSTANCE.load();
    }
}
