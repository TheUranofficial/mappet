package com.theuran.mappet;

import com.mojang.logging.LogUtils;
import com.theuran.mappet.api.events.EventHandler;
import com.theuran.mappet.api.events.EventManager;
import com.theuran.mappet.api.executables.ExecutableManager;
import com.theuran.mappet.api.huds.HUDManager;
import com.theuran.mappet.api.keybinds.KeybindManager;
import com.theuran.mappet.api.localization.LocalizationManager;
import com.theuran.mappet.api.scripts.ScriptManager;
import com.theuran.mappet.api.scripts.logger.LoggerManager;
import com.theuran.mappet.api.states.StatesManager;
import com.theuran.mappet.api.triggers.*;
import com.theuran.mappet.api.ui.UIManager;
import com.theuran.mappet.block.MappetBlockEntities;
import com.theuran.mappet.block.MappetBlocks;
import com.theuran.mappet.client.ai.AiMain;
import com.theuran.mappet.item.MappetItemGroups;
import com.theuran.mappet.item.MappetItems;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.HandshakeS2CPacket;
import com.theuran.mappet.resources.packs.MappetInternalAssetsPack;
import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.resources.AssetProvider;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.utils.colors.Colors;
import mchorse.bbs_mod.utils.factory.MapFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import org.slf4j.Logger;

import java.io.File;

public class Mappet implements ModInitializer {
    public static final String MOD_ID = "mappet";

    public static final Logger LOGGER = LogUtils.getLogger();

    private static File settingsFolder;
    private static File assetsFolder;
    private static File mappetFolder;

    private static AssetProvider provider;

    private static StatesManager states;
    private static HUDManager huds;
    private static ScriptManager scripts;
    private static UIManager uis;
    private static LoggerManager logger;
    private static EventManager events;
    private static KeybindManager keybinds;
    private static LocalizationManager localizations;
    private static ExecutableManager executables;

    private static MapFactory<Trigger, Integer> triggers;

    @Override
    public void onInitialize() {
        Dispatcher.register();

        AiMain.init();

        settingsFolder = BBSMod.getGamePath("config/mappet/settings");
        settingsFolder.mkdirs();
        assetsFolder = BBSMod.getGamePath("config/mappet/assets");
        assetsFolder.mkdirs();

        provider = new AssetProvider();
        provider.register(new MappetInternalAssetsPack());

        huds = new HUDManager(() -> new File(mappetFolder, "huds"));
        scripts = new ScriptManager(() -> new File(mappetFolder, "scripts"));
        uis = new UIManager(() -> new File(mappetFolder, "uis"));
        states = new StatesManager(() -> new File(mappetFolder, "states.json"));
        events = new EventManager(() -> new File(mappetFolder, "events.json"));
        keybinds = new KeybindManager(() -> new File(mappetFolder, "keybinds.json"));
        localizations = new LocalizationManager(() -> new File(mappetFolder, "localization.json"));
        logger = new LoggerManager();
        executables = new ExecutableManager();

        triggers = new MapFactory<>();
        triggers
                .register(link("command"), CommandTrigger.class, 0x942aff)
                .register(link("item"), ItemTrigger.class, 0xff6600)
                .register(link("script"), ScriptTrigger.class, 0x2d4163)
                .register(link("sound"), SoundTrigger.class, 0x00a0ff)
                .register(link("state"), StateTrigger.class, Colors.NEGATIVE);

        EventHandler.init();

        MappetItems.init();
        MappetItemGroups.init();
        MappetBlocks.init();
        MappetBlockEntities.init();

        //BBSMod.setupConfig(Icons.PLANE, Mappet.MOD_ID, new File(settingsFolder, "mappet.json"), MappetSettings::register);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            mappetFolder = new File(server.getSavePath(WorldSavePath.ROOT).toFile(), "mappet");

            mappetFolder.mkdirs();

            states.load();
            events.load();
            keybinds.load();

            scripts.initialize();
        });

        ServerLifecycleEvents.BEFORE_SAVE.register((server, flush, force) -> {
            states.save();
            events.save();
            keybinds.save();
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Dispatcher.sendTo(new HandshakeS2CPacket(), handler.getPlayer());
            Mappet.getScripts().sendClientScripts(handler.getPlayer());
        });

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

    public static StatesManager getStates() {
        return states;
    }

    public static File getAssetsFolder() {
        return assetsFolder;
    }

    public static File getSettingsFolder() {
        return settingsFolder;
    }

    public static HUDManager getHuds() {
        return huds;
    }

    public static ScriptManager getScripts() {
        return scripts;
    }

    public static UIManager getUIs() {
        return uis;
    }

    public static LoggerManager getLogger() {
        return logger;
    }

    public static EventManager getEvents() {
        return events;
    }

    public static ExecutableManager getExecutables() {
        return executables;
    }

    public static KeybindManager getKeybinds() {
        return keybinds;
    }

    public static LocalizationManager getLocalizations() {
        return localizations;
    }

    public static MapFactory<Trigger, Integer> getTriggers() {
        return triggers;
    }
}