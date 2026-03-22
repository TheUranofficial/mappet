package com.theuran.mappet.utils;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.huds.HUDsClosePacket;
import com.theuran.mappet.network.packets.huds.HUDsFormPacket;
import com.theuran.mappet.network.packets.huds.HUDsSetupPacket;
import mchorse.bbs_mod.forms.forms.Form;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class PlayerUtils {
    public static void setupHUD(ServerPlayerEntity player, String id) {
        HUDScene hud = Mappet.getHuds().load(id);

        if (hud != null) {
            Dispatcher.sendTo(new HUDsSetupPacket(id, hud.toData()), player);
        }
    }

    public static void closeHUD(ServerPlayerEntity player, String id) {
        Dispatcher.sendTo(new HUDsClosePacket(id, false), player);
    }

    public static void closeAllHUDs(ServerPlayerEntity player) {
        Dispatcher.sendTo(new HUDsClosePacket("", true), player);
    }

    public static void changeHUDForm(ServerPlayerEntity player, String id, int index, Form form) {
        Dispatcher.sendTo(new HUDsFormPacket(id, index, form), player);
    }

    public static String executeScript(String scriptName, Entity entity, ServerWorld world, MinecraftServer server) {
        ScriptEvent properties = ScriptEvent.create(scriptName, "main", entity, null, world, server);

        try {
            Mappet.getScripts().execute(properties);
        } catch (JavetException e) {
            return e.getLocalizedMessage();
        }

        return "0";
    }

    public static String executeEval(String code, Entity entity, ServerWorld world, MinecraftServer server) {
        ScriptEvent properties = ScriptEvent.create("~", "", entity, null, world, server);

        try {
            Mappet.getScripts().eval(code, properties);
        } catch (JavetException e) {
            return e.getLocalizedMessage();
        }

        return "0";
    }
}