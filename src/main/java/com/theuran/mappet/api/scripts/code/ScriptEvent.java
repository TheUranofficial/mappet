package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import java.util.HashMap;
import java.util.Map;

public class ScriptEvent {
    private final String script;
    private final String function;

    public final ScriptEntity subject;
    public final ScriptEntity object;
    public final ScriptWorld world;
    public final ScriptServer server;

    private Map<String, Object> values = new HashMap<>();

    public ScriptEvent(String script, String function, ScriptEntity<?> subject, ScriptEntity<?> object, ScriptWorld world, ScriptServer server) {
        this.script = script;
        this.function = function;

        this.subject = subject;
        this.object = object;
        this.world = world;
        this.server = server;
    }

    public static ScriptEvent create(String script, String function, Entity subject, Entity object, ServerWorld world, MinecraftServer server) {
        return new ScriptEvent(
                script,
                function,
                ScriptEntity.create(subject),
                ScriptEntity.create(object),
                world == null ? null : new ScriptWorld(world),
                server == null ? null : new ScriptServer(server)
        );
    }

    public String getScript() {
        return this.script;
    }

    public String getFunction() {
        return this.function;
    }

    public ScriptEntity getSubject() {
        return this.subject;
    }

    public ScriptEntity getObject() {
        return this.object;
    }

    public ScriptWorld getWorld() {
        return this.world;
    }

    public ScriptServer getServer() {
        return this.server;
    }

    public void set(String key, Object value) {
        this.values.put(key, value);
    }

    public Object get(String key) {
        return this.values.get(key);
    }

    public void send(String message) {
        if (this.server != null) {
            this.server.send(message);
        }
    }
}
