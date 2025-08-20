package com.theuran.mappet;

import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Mappet implements ModInitializer {
	public static final String MOD_ID = "mappet";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static File settingsFolder;

	@Override
	public void onInitialize() {
        settingsFolder = BBSMod.getGamePath("config/mappet/settings");
        settingsFolder.mkdirs();

        BBSMod.setupConfig(Icons.PLANE, MOD_ID, new File(settingsFolder, "mappet.json"), MappetSettings::register);
	}
}