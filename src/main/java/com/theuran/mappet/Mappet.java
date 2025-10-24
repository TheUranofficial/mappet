package com.theuran.mappet;

import com.mojang.logging.LogUtils;
import com.theuran.mappet.api.huds.HUDManager;
import com.theuran.mappet.api.scripts.ScriptManager;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.MappetServerNetwork;
import com.theuran.mappet.resources.packs.MappetInternalAssetsPack;
import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.resources.AssetProvider;
import mchorse.bbs_mod.resources.Link;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import org.slf4j.Logger;

import java.io.File;

public class Mappet implements ModInitializer {
    public static final String MOD_ID = "mappet";

    public static final Logger LOGGER = LogUtils.getLogger();

    private static File settingsFolder;
    private static File assetsFolder;
    private static File worldFolder;
    private static File mappetFolder;

    private static AssetProvider provider;

    private static States states;
    private static HUDManager huds;
    private static ScriptManager scripts;

    @Override
    public void onInitialize() {
        Dispatcher.register();

        settingsFolder = BBSMod.getGamePath("config/mappet/settings");
        settingsFolder.mkdirs();
        assetsFolder = BBSMod.getGamePath("config/mappet/assets");
        assetsFolder.mkdirs();

        provider = new AssetProvider();
        provider.register(new MappetInternalAssetsPack());

        huds = new HUDManager(() -> new File(mappetFolder, "huds"));

        scripts = new ScriptManager(() -> new File(mappetFolder, "scripts"));

        //BBSMod.setupConfig(Icons.PLANE, Mappet.MOD_ID, new File(settingsFolder, "mappet.json"), MappetSettings::register);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            worldFolder = server.getSavePath(WorldSavePath.ROOT).toFile();
            mappetFolder = new File(worldFolder, "mappet");

            mappetFolder.mkdirs();

            states = new States(new File(mappetFolder, "states.json"));
            states.load();
        });

        ServerLifecycleEvents.BEFORE_SAVE.register((server, flush, force) -> {
            states.save();
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            worldFolder = null;
            states = null;

            MappetServerNetwork.reset();
        });

        MappetServerNetwork.setup();

        CommandRegistrationCallback.EVENT.register(MappetCommands::register);
    }

    public static Link link(String path) {
        return new Link(MOD_ID, path);
    }

    public static Identifier id(String path) {
        return new Identifier(Mappet.MOD_ID, path);
    }

    public static AssetProvider getProvider() {
        return provider;
    }

    public static States getStates() {
        return states;
    }

    public static File getAssetsFolder() {
        return assetsFolder;
    }

    public static File getSettingsFolder() {
        return settingsFolder;
    }

    public static File getWorldFolder() {
        return worldFolder;
    }

    public static HUDManager getHuds() {
        return huds;
    }

    public static ScriptManager getScripts() {
        return scripts;
    }
}