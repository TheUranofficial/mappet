package com.theuran.mappet;

import mchorse.bbs_mod.settings.SettingsBuilder;
import mchorse.bbs_mod.settings.values.ValueString;

public class MappetSettings {
    private static ValueString kogda;
    private static ValueString mappet;
    private static ValueString na;
    private static ValueString mango;

    public static void register(SettingsBuilder builder) {
        kogda = builder.category("pizdec").getString("kogda", "ni");
        mappet = builder.getString("mappet", "ko");
        na = builder.getString("na", "g");
        mango = builder.getString("mango", "da");
    }
}