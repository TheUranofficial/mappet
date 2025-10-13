package com.theuran.mappet;

import com.theuran.mappet.api.states.States;
import com.theuran.mappet.resources.packs.MappetInternalAssetsPack;
import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.resources.AssetProvider;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.WorldSavePath;

import java.io.File;

public class Mappet implements ModInitializer {
    public static final String MOD_ID = "mappet";

    private static File settingsFolder;
    private static File worldFolder;

    private static AssetProvider provider;

    private static States states;

    @Override
    public void onInitialize() {
        settingsFolder = BBSMod.getGamePath("config/mappet/settings");
        settingsFolder.mkdirs();

        provider = new AssetProvider();
        provider.register(new MappetInternalAssetsPack());

        //BBSMod.setupConfig(Icons.PLANE, Mappet.MOD_ID, new File(settingsFolder, "mappet.json"), MappetSettings::register);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                    worldFolder = new File(server.getSavePath(WorldSavePath.ROOT).toFile(), Mappet.MOD_ID);
                    worldFolder.mkdirs();

                    states = new States(new File(worldFolder, "states.json"));
                    states.load();
                }
        );
        ServerLifecycleEvents.BEFORE_SAVE.register((server, flush, force) -> {
            states.save();
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            worldFolder = null;
            states = null;
        });

        CommandRegistrationCallback.EVENT.register(MappetCommands::register);
    }

    public static Link link(String path) {
        return new Link(Mappet.MOD_ID, path);
    }

    public static AssetProvider getProvider() {
        return Mappet.provider;
    }

    public static States getStates() {
        return Mappet.states;
    }
}