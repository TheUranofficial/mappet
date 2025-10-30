package com.theuran.mappet.utils.keys;

import java.util.ArrayList;
import java.util.List;

public class Keybind {
    private String id;
    private final List<Key> keys = new ArrayList<>();

    public Keybind(Key... keys) {
        this.keys.addAll(List.of(keys));
    }

    public Keybind(String id, Key... keys) {
        this.id = id;
        this.keys.addAll(List.of(keys));
    }

    public String getId() {
        return this.id;
    }

    public List<Key> getKeys() {
        return this.keys;
    }

    public Keybind key(Key key) {
        this.keys.add(key);
        return this;
    }
}
