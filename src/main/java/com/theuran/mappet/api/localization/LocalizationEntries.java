package com.theuran.mappet.api.localization;

import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;

import java.util.ArrayList;
import java.util.List;

public class LocalizationEntries implements IMapSerializable {
    private List<LocalizationEntry> entries = new ArrayList<>();

    public void add(String key, String value) {
        this.entries.add(new LocalizationEntry(key, value));
    }

    public List<LocalizationEntry> getAll() {
        return this.entries;
    }

    public String getValue(String key) {
        return this.entries.stream()
            .filter(e -> e.getId().equals(key))
            .map(LocalizationEntry::getText)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void toData(MapType data) {

    }

    @Override
    public void fromData(MapType data) {

    }
}