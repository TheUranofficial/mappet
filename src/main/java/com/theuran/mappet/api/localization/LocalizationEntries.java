package com.theuran.mappet.api.localization;

import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;

import java.util.ArrayList;
import java.util.List;

public class LocalizationEntries implements IMapSerializable {
    private final List<LocalizationEntry> entries = new ArrayList<>();

    public void add(String key, String value) {
        entries.add(new LocalizationEntry(key, value));
    }

    public List<LocalizationEntry> getAll() {
        return entries;
    }

    public String getValue(String key) {
        return entries.stream()
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