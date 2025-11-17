package com.theuran.mappet.client;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

public class ClientEventHandler {
    public static void init() {
        player();
        client();
    }

    private static void client() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Mappet.getEvents().noTriggersInEvent(EventType.CLIENT_TICK))
                return;

            ClientScriptEvent scriptEvent = ClientScriptEvent.create(client.player, null, client.world);

            Mappet.getEvents().eventClient(EventType.CLIENT_TICK, scriptEvent);
        });
    }

    private static void player() {
        AttackBlockCallback.EVENT.register((player, world, hand, blockPos, direction) -> {
            if (Mappet.getEvents().noTriggersInEvent(EventType.CLIENT_ATTACK_BLOCK))
                return ActionResult.PASS;

            ClientScriptEvent scriptEvent = ClientScriptEvent.create(player, null, (ClientWorld) world);

            scriptEvent.setValue("hand", modifyHand(hand));
            scriptEvent.setValue("blockPos", new ScriptVector(blockPos));
            scriptEvent.setValue("direction", direction.getName());

            Mappet.getEvents().eventClient(EventType.CLIENT_ATTACK_BLOCK, scriptEvent);

            return scriptEvent.getResultType();
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (Mappet.getEvents().noTriggersInEvent(EventType.CLIENT_ATTACK_ENTITY))
                return ActionResult.PASS;

            ClientScriptEvent scriptEvent = ClientScriptEvent.create(player, entity, (ClientWorld) world);

            scriptEvent.setValue("hand", modifyHand(hand));

            Mappet.getEvents().eventClient(EventType.CLIENT_ATTACK_ENTITY, scriptEvent);

            return scriptEvent.getResultType();
        });

        UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
            if (Mappet.getEvents().noTriggersInEvent(EventType.CLIENT_USE_BLOCK))
                return ActionResult.PASS;

            ClientScriptEvent scriptEvent = ClientScriptEvent.create(player, null, (ClientWorld) world);

            scriptEvent.setValue("hand", modifyHand(hand));
            scriptEvent.setValue("blockPos", new ScriptVector(blockHitResult.getBlockPos()));
            scriptEvent.setValue("direction", blockHitResult.getSide().getName().toLowerCase());

            Mappet.getEvents().eventClient(EventType.CLIENT_USE_BLOCK, scriptEvent);

            return scriptEvent.getResultType();
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (Mappet.getEvents().noTriggersInEvent(EventType.CLIENT_USE_ENTITY))
                return ActionResult.PASS;

            ClientScriptEvent scriptEvent = ClientScriptEvent.create(player, null, (ClientWorld) world);

            scriptEvent.setValue("hand", modifyHand(hand));

            Mappet.getEvents().eventClient(EventType.CLIENT_USE_ENTITY, scriptEvent);

            return scriptEvent.getResultType();
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (Mappet.getEvents().noTriggersInEvent(EventType.CLIENT_USE_ITEM))
                return TypedActionResult.pass(player.getStackInHand(hand));

            ClientScriptEvent scriptEvent = ClientScriptEvent.create(player, null, (ClientWorld) world);

            scriptEvent.setValue("hand", modifyHand(hand));
            scriptEvent.setValue("item", stack);

            Mappet.getEvents().eventClient(EventType.CLIENT_USE_ITEM, scriptEvent);

            return new TypedActionResult<>(scriptEvent.getResultType(), stack);
        });
    }


    private static String modifyHand(Hand hand) {
        return hand.name().replace("_HAND", "").toLowerCase();
    }
}
