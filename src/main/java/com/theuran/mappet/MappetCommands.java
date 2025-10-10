package com.theuran.mappet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.function.Predicate;

public class MappetCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        Predicate<ServerCommandSource> hasPermissions = (source) -> source.hasPermissionLevel(2);
        LiteralArgumentBuilder<ServerCommandSource> mappet = CommandManager.literal(Mappet.MOD_ID).requires((source) -> true);

        registerStatesCommands(mappet, environment, hasPermissions);

        dispatcher.register(mappet);
    }

    private static void registerStatesCommands(LiteralArgumentBuilder<ServerCommandSource> mappet, CommandManager.RegistrationEnvironment environment, Predicate<ServerCommandSource> hasPermissions) {
        LiteralArgumentBuilder<ServerCommandSource> states = CommandManager.literal("states");
        LiteralArgumentBuilder<ServerCommandSource> set = CommandManager.literal("set");
        LiteralArgumentBuilder<ServerCommandSource> get = CommandManager.literal("get");
        LiteralArgumentBuilder<ServerCommandSource> server = CommandManager.literal("server");

        RequiredArgumentBuilder<ServerCommandSource, EntitySelector> player = CommandManager.argument("player", EntityArgumentType.player());
        RequiredArgumentBuilder<ServerCommandSource, String> setKey = CommandManager.argument("key", StringArgumentType.word());
        RequiredArgumentBuilder<ServerCommandSource, String> getKey = CommandManager.argument("key", StringArgumentType.word());
        RequiredArgumentBuilder<ServerCommandSource, String> value = CommandManager.argument("value", StringArgumentType.string());

        //What the heck I write
        states.then(
                player.then(
                        set.then(
                                setKey.then(
                                        value.executes(MappetCommands::statesSetCommand)
                                )
                        )
                ).then(
                        get.then(
                                getKey.executes(MappetCommands::statesGetCommand)
                        )
                )
        ).then(
                server.then(
                        set.then(
                                setKey.then(
                                        value.executes(MappetCommands::statesSetCommand)
                                )
                        )
                ).then(
                        get.then(
                                getKey.executes(MappetCommands::statesGetCommand)
                        )
                )
        );

        mappet.then(states.requires(hasPermissions));
    }

    private static int statesGetCommand(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> {
            try {
                MapType states = context.getInput().contains("server") ? Mappet.getStates().getValues() : Mappet.getStates().getEntityStates(EntityArgumentType.getPlayer(context, "player"));

                return Text.literal("Value is: %s".formatted(states.get(StringArgumentType.getString(context, "key"))));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }, false);

        return 1;
    }

    private static int statesSetCommand(CommandContext<ServerCommandSource> context) {
        try {
            String value = StringArgumentType.getString(context, "value");
            MapType states = context.getInput().contains("server") ? Mappet.getStates().getValues() : Mappet.getStates().getEntityStates(EntityArgumentType.getPlayer(context, "player"));
            String key =  StringArgumentType.getString(context, "key");

            try {
                Mappet.getStates().setNumber(states, key, Double.parseDouble(value));
            } catch (NumberFormatException e) {
                Mappet.getStates().setString(states, key, value);
            }
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}
