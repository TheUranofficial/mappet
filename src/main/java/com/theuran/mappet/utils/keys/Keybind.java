package com.theuran.mappet.utils.keys;

import java.util.ArrayList;
import java.util.List;

public class Keybind {
    public List<Key> keys = new ArrayList<>();

    public Keybind(Key... keys) {
        this.keys.addAll(List.of(keys));
    }

    public Keybind() {

    }

    public Keybind key(Key key) {
        this.keys.add(key);
        return this;
    }
}
