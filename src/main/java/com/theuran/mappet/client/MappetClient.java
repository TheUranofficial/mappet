package com.theuran.mappet.client;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.api.keybinds.ClientKeybindManager;
import com.theuran.mappet.client.api.scripts.ClientScriptManager;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import com.theuran.mappet.client.keybinds.MappetKeybinds;
import com.theuran.mappet.client.ui.UIMappetBase;
import com.theuran.mappet.client.ui.UIMappetDashboard;
import com.theuran.mappet.client.ui.panels.UIScriptPanel;
import com.theuran.mappet.item.MappetItems;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RunScriptPacket;
import com.theuran.mappet.utils.InputUtils;
import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.time.LocalDate;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MappetClient implements ClientModInitializer {
    private static UIMappetDashboard dashboard;

    private static L10n l10n;

    private static ClientScriptManager scripts;
    private static ClientKeybindManager keybinds;

    public static UIMappetDashboard getDashboard() {
        if (dashboard == null)
            dashboard = new UIMappetDashboard();

        return dashboard;
    }

    public static L10n getL10n() {
        return l10n;
    }

    @Override
    public void onInitializeClient() {
        Dispatcher.register();

        l10n = new L10n();
        l10n.registerOne((lang) -> Mappet.link("lang/" + lang + ".json"));
        l10n.reload(BBSSettings.language.get(), Mappet.getProvider());

        MappetKeybinds.init();

        scripts = new ClientScriptManager();
        keybinds = new ClientKeybindManager();

        InputUtils.init();

        ModelPredicateProviderRegistry.register(
                MappetItems.HAMMER,
                new Identifier("date"),
                (stack, world, entity, seed) -> {
                    LocalDate now = LocalDate.now();
                    int day = now.getDayOfMonth();
                    int month = now.getMonthValue();

                    float value = 0.0f;

                    if (month == 12 && day == 1) {
                        value = 0.2f;
                    } else if (month == 5 && day == 2) {
                        value = 0.4f;
                    } else if (month == 11 && day == 12) {
                        value = 0.6f;
                    } else if (month == 4 && day == 22) {
                        value = 0.8f;
                    } else if (month == 12 && day == 2) {
                        value = 1f;
                    }

                    return value;
                }
        );

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            dashboard = null;

            Dispatcher.isMappetModOnServer = false;
        });
    }

    public static ClientScriptManager getScripts() {
        return scripts;
    }

    public static ClientKeybindManager getKeybinds() {
        return keybinds;
    }
}
