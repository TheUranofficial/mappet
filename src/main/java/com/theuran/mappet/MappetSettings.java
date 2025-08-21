package com.theuran.mappet;

import mchorse.bbs_mod.settings.SettingsBuilder;

public class MappetSettings {
    public static void register(SettingsBuilder builder) {
        builder.category("pizdec").getString("kogda", "ni");
        builder.getString("mappet", "ko");
        builder.getString("na", "g");
        builder.getString("mango", "da");
    }
}