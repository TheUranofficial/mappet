package com.theuran.mappet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.utils.BooleanUtils;
import com.theuran.mappet.utils.FormUtils;
import com.theuran.mappet.utils.PlayerUtils;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.forms.forms.Form;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.function.Predicate;

public class MappetCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        Predicate<ServerCommandSource> hasPermissions = (source) -> source.hasPermissionLevel(2);
        LiteralArgumentBuilder<ServerCommandSource> mappet = CommandManager.literal(Mappet.MOD_ID).requires((source) -> true);

        registerStatesCommands(mappet, environment, hasPermissions);
        registerScriptCommands(mappet, environment, hasPermissions);
        registerHudCommands(mappet, environment, hasPermissions);

        dispatcher.register(mappet);
    }

    private static void registerHudCommands(LiteralArgumentBuilder<ServerCommandSource> mappet, CommandManager.RegistrationEnvironment environment, Predicate<ServerCommandSource> hasPermissions) {
        mappet.then(CommandManager.literal("hud")
            .requires(hasPermissions)
            .then(CommandManager.literal("setup")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .then(CommandManager.argument("id", StringArgumentType.word())
                        .executes(MappetCommands::hudSetupCommand)
                    )
                )
            )
            .then(CommandManager.literal("close")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .executes(MappetCommands::hudCloseAllCommand)
                    .then(CommandManager.argument("id", StringArgumentType.word())
                        .executes(MappetCommands::hudCloseCommand)
                    )
                )
            )
            .then(CommandManager.literal("form")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .then(CommandManager.argument("id", StringArgumentType.word())
                        .then(CommandManager.argument("index", IntegerArgumentType.integer(0))
                            .then(CommandManager.argument("form", StringArgumentType.greedyString())
                                .executes(MappetCommands::hudFormCommand)
                            )
                        )
                    )
                )
            )
        );
    }

    private static int hudSetupCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        String id = StringArgumentType.getString(context, "id");

        PlayerUtils.setupHUD(player, id);

        return 1;
    }

    private static int hudCloseAllCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");

        PlayerUtils.closeAllHUDs(player);

        return 1;
    }

    private static int hudCloseCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        String id = StringArgumentType.getString(context, "id");

        PlayerUtils.closeHUD(player, id);

        return 1;
    }

    private static int hudFormCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        String id = StringArgumentType.getString(context, "id");
        int index = IntegerArgumentType.getInteger(context, "index");
        Form form = FormUtils.fromData(StringArgumentType.getString(context, "form"));

        PlayerUtils.changeHUDForm(player, id, index, form);

        return 1;
    }

    private static void registerScriptCommands(LiteralArgumentBuilder<ServerCommandSource> mappet, CommandManager.RegistrationEnvironment environment, Predicate<ServerCommandSource> hasPermissions) {
        mappet.then(CommandManager.literal("eval")
            .requires(hasPermissions)
            .then(CommandManager.argument("code", StringArgumentType.greedyString())
                .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        String code = StringArgumentType.getString(context, "code");

                        String error = PlayerUtils.executeEval(code, source.getEntity(), source.getWorld(), source.getServer());

                        if (!error.equals("0")) {
                            source.sendFeedback(() -> Text.of(error), false);
                        }

                        return 1;
                    }
                )
            )
        );

        mappet.then(CommandManager.literal("script")
            .requires(hasPermissions)
            .then(CommandManager.argument("name", StringArgumentType.word())
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    String scriptName = StringArgumentType.getString(context, "name");

                    String error = PlayerUtils.executeScript(scriptName, source.getEntity(), source.getWorld(), source.getServer());

                    if (!error.equals("0")) {
                        source.sendFeedback(() -> Text.of(error), false);
                    }

                    return 1;
                })
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
            States statesInstance = context.getInput().contains("~") ? Mappet.getStates().get() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();

            for (String key : statesInstance.keys()) {
                builder.suggest(key);
            }

            return builder.buildFuture();
        });

        getKey.suggests((context, builder) -> {
            States statesInstance = context.getInput().contains("~") ? Mappet.getStates().get() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();

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
                States states = context.getInput().contains("~") ? Mappet.getStates().get() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();
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
            States states = context.getInput().contains("~") ? Mappet.getStates().get() : ((IStatesProvider) EntityArgumentType.getPlayer(context, "player")).getStates();
            String key = StringArgumentType.getString(context, "key");

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
