package com.theuran.mappet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.theuran.mappet.api.scripts.ScriptManager;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.utils.BooleanUtils;
import mchorse.bbs_mod.data.types.BaseType;
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
        registerScriptCommands(mappet, environment, hasPermissions);

        dispatcher.register(mappet);
    }

    private static void registerScriptCommands(LiteralArgumentBuilder<ServerCommandSource> mappet, CommandManager.RegistrationEnvironment environment, Predicate<ServerCommandSource> hasPermissions) {
        mappet.then(CommandManager.literal("eval")
                .requires(hasPermissions)
                .then(CommandManager.argument("code", StringArgumentType.greedyString())
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            String code = StringArgumentType.getString(context, "code");

                            source.sendFeedback(() -> Text.literal(new ScriptManager().evalCode(code)), false);

                            return 1;
                        }
                    )
                )
        );
    }

    private static void registerStatesCommands(LiteralArgumentBuilder<ServerCommandSource> mappet, CommandManager.RegistrationEnvironment environment, Predicate<ServerCommandSource> hasPermissions) {
        LiteralArgumentBuilder<ServerCommandSource> states = CommandManager.literal("states");
        LiteralArgumentBuilder<ServerCommandSource> set = CommandManager.literal("set");
        LiteralArgumentBuilder<ServerCommandSource> get = CommandManager.literal("get");
        LiteralArgumentBuilder<ServerCommandSource> server = CommandManager.literal("~");

        RequiredArgumentBuilder<ServerCommandSource, EntitySelector> player = CommandManager.argument("player", EntityArgumentType.player());
        RequiredArgumentBuilder<ServerCommandSource, String> setKey = CommandManager.argument("key", StringArgumentType.word());
        RequiredArgumentBuilder<ServerCommandSource, String> getKey = CommandManager.argument("key", StringArgumentType.word());
        RequiredArgumentBuilder<ServerCommandSource, String> value = CommandManager.argument("value", StringArgumentType.string());

        //bruh
        setKey.suggests((context, builder) -> {
            States statesInstance = context.getInput().contains("~") ? Mappet.getStates() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();

            for (String key : statesInstance.keys()) {
                builder.suggest(key);
            }

            return builder.buildFuture();
        });

        getKey.suggests((context, builder) -> {
            States statesInstance = context.getInput().contains("~") ? Mappet.getStates() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();

            for (String key : statesInstance.keys()) {
                builder.suggest(key);
            }

            return builder.buildFuture();
        });

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
                        set
                ).then(
                        get
                )
        );

        mappet.then(states.requires(hasPermissions));
    }

    private static int statesGetCommand(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> {
            try {
                States states = context.getInput().contains("~") ? Mappet.getStates() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();
                BaseType value = states.get(StringArgumentType.getString(context, "key"));

                return Text.literal("Value is: %s".formatted(value));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }, false);

        return 1;
    }

    private static int statesSetCommand(CommandContext<ServerCommandSource> context) {
        try {
            String value = StringArgumentType.getString(context, "value");
            States states = context.getInput().contains("~") ? Mappet.getStates() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();
            String key =  StringArgumentType.getString(context, "key");

            try {
                states.setNumber(key, Double.parseDouble(value));
            } catch (NumberFormatException e) {
                if (BooleanUtils.isBoolean(value)) {
                    states.setBoolean(key, Boolean.parseBoolean(value));
                } else {
                    states.setString(key, value);
                }
            }
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}
