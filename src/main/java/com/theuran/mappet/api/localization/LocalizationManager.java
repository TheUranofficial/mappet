package com.theuran.mappet.api.localization;

import com.theuran.mappet.utils.BaseFileManager;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.ListType;
import mchorse.bbs_mod.data.types.MapType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LocalizationManager extends BaseFileManager {
    Map<LocalizationType, LocalizationEntries> lang;

    public LocalizationManager(Supplier<File> file) {
        super(file);

        this.lang = new HashMap<>();

        for (LocalizationType localizationType : LocalizationType.values()) {
            this.registerLocalization(localizationType);
        }
    }

    public String getLocalization(String id, LocalizationType localizationType) {
        return lang.get(localizationType).getValue(id);
    }

    private void registerLocalization(LocalizationType localizationType) {
        this.lang.put(localizationType, new LocalizationEntries());
    }

    @Override
    public void toData(MapType data) {
        this.lang.forEach((type, entries) -> {
            ListType localizationEntries = new ListType();

            for (LocalizationEntry entry : entries.getAll()) {
                localizationEntries.add(entry.toData());
            }

            data.put(type.name(), localizationEntries);
        });
    }

    @Override
    public void fromData(MapType data) {
        for (Map.Entry<String, BaseType> event : data) {
            LocalizationEntries localizationEntries = new LocalizationEntries();

            for (BaseType type : event.getValue().asList()) {
                MapType mapType = type.asMap();

                localizationEntries.add(
                    mapType.getString("id"),
                    mapType.getString("text")
                );
            }

            this.lang.put(LocalizationType.valueOf(event.getKey()), localizationEntries);
        }
    }
}