package com.theuran.mappet.api.events;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EventHandler {
    public static void init() {
        entity();
        server();
        player();
    }

    private static void entity() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((livingEntity, damageSource, damage) -> {
            ScriptEvent scriptEvent = ScriptEvent.create(damageSource.getAttacker(), damageSource.getSource(), (ServerWorld) damageSource.getAttacker().getWorld(), damageSource.getAttacker().getServer());

            scriptEvent.setValue("damage", damage);
            scriptEvent.setValue("damageType", damageSource.getName());

            Mappet.getEvents().event(EventType.ENTITY_DAMAGE, scriptEvent);

            return scriptEvent.getResultType().isAccepted();
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            ScriptEvent scriptEvent = ScriptEvent.create(damageSource.getAttacker(), damageSource.getSource(), (ServerWorld) damageSource.getAttacker().getWorld(), damageSource.getAttacker().getServer());

            scriptEvent.setValue("damage", damageAmount);
            scriptEvent.setValue("deathType", damageSource.getName());

            Mappet.getEvents().event(EventType.ENTITY_DEATH, scriptEvent);

            return scriptEvent.getResultType().isAccepted();
        });
    }

    private static void server() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ScriptEvent scriptEvent = ScriptEvent.create(null, null, server.getWorld(World.OVERWORLD), server);

            Mappet.getEvents().event(EventType.SERVER_TICK, scriptEvent);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ScriptEvent scriptEvent = ScriptEvent.create(null, null, server.getWorld(World.OVERWORLD), server);

            Mappet.getEvents().event(EventType.SERVER_STARTED, scriptEvent);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ScriptEvent scriptEvent = ScriptEvent.create(null, null, server.getWorld(World.OVERWORLD), server);

            Mappet.getEvents().event(EventType.SERVER_STOPPED, scriptEvent);
        });
    }

    private static void player() {
        AttackBlockCallback.EVENT.register((player, world, hand, blockPos, direction) -> {
            if (world.isClient || Mappet.getEvents().noTriggersInEvent(EventType.PLAYER_ATTACK_BLOCK))
                return ActionResult.PASS;

            ScriptEvent scriptEvent = ScriptEvent.create(player, null, (ServerWorld) world, player.getServer());

            scriptEvent.setValue("hand", modifyHand(hand));
            scriptEvent.setValue("blockPos", new ScriptVector(blockPos));
            scriptEvent.setValue("direction", direction.getName());

            Mappet.getEvents().event(EventType.PLAYER_ATTACK_BLOCK, scriptEvent);

            return scriptEvent.getResultType();
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (world.isClient || Mappet.getEvents().noTriggersInEvent(EventType.PLAYER_ATTACK_ENTITY))
                return ActionResult.PASS;

            ScriptEvent scriptEvent = ScriptEvent.create(player, entity, (ServerWorld) world, player.getServer());

            scriptEvent.setValue("hand", modifyHand(hand));

            Mappet.getEvents().event(EventType.PLAYER_ATTACK_ENTITY, scriptEvent);

            return scriptEvent.getResultType();
        });

        UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
            if (world.isClient || Mappet.getEvents().noTriggersInEvent(EventType.PLAYER_USE_BLOCK))
                return ActionResult.PASS;

            ScriptEvent scriptEvent = ScriptEvent.create(player, null, (ServerWorld) world, player.getServer());

            scriptEvent.setValue("hand", modifyHand(hand));
            scriptEvent.setValue("blockPos", new ScriptVector(blockHitResult.getBlockPos()));
            scriptEvent.setValue("direction", blockHitResult.getSide().getName().toLowerCase());

            Mappet.getEvents().event(EventType.PLAYER_USE_BLOCK, scriptEvent);

            return scriptEvent.getResultType();
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (world.isClient || Mappet.getEvents().noTriggersInEvent(EventType.PLAYER_USE_ENTITY))
                return ActionResult.PASS;

            ScriptEvent scriptEvent = ScriptEvent.create(player, null, (ServerWorld) world, player.getServer());

            scriptEvent.setValue("hand", modifyHand(hand));

            Mappet.getEvents().event(EventType.PLAYER_USE_ENTITY, scriptEvent);

            return scriptEvent.getResultType();
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if(world.isClient || Mappet.getEvents().noTriggersInEvent(EventType.PLAYER_USE_ITEM))
                return TypedActionResult.pass(player.getStackInHand(hand));

            ScriptEvent scriptEvent = ScriptEvent.create(player, null, (ServerWorld) world, player.getServer());

            ItemStack stack = player.getStackInHand(hand);

            scriptEvent.setValue("hand", modifyHand(hand));
            scriptEvent.setValue("item", stack);

            Mappet.getEvents().event(EventType.PLAYER_USE_ITEM, scriptEvent);

            return switch (scriptEvent.getResultType()) {
                case SUCCESS -> TypedActionResult.success(stack, true);
                case CONSUME_PARTIAL -> TypedActionResult.success(stack, false);
                case CONSUME -> TypedActionResult.consume(stack);
                case FAIL -> TypedActionResult.fail(stack);
                case PASS -> TypedActionResult.pass(stack);
            };
        });
    }

    private static String modifyHand(Hand hand) {
        return hand.name().replace("_HAND", "").toLowerCase();
    }
}
