package com.theuran.mappet;

import com.theuran.mappet.api.states.States;
import com.theuran.mappet.resources.packs.MappetInternalAssetsPack;
import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.resources.AssetProvider;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.ModInitializer;

import java.io.File;

public class Mappet implements ModInitializer {
	public static final String MOD_ID = "mappet";

    private static File settingsFolder;

    private static AssetProvider provider;

    private static States states;

	@Override
	public void onInitialize() {
        settingsFolder = BBSMod.getGamePath("config/mappet/settings");
        settingsFolder.mkdirs();

        provider = new AssetProvider();
        provider.register(new MappetInternalAssetsPack());

        BBSMod.setupConfig(Icons.PLANE, MOD_ID, new File(settingsFolder, "mappet.json"), MappetSettings::register);
	}

    public static Link link(String path) {
        return new Link(MOD_ID, path);
    }

    public static AssetProvider getProvider() {
        return provider;
    }

    public static States getStates() {
        return states;
    }
}