package com.theuran.mappet.api.scripts.code;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

import java.util.HashMap;
import java.util.Map;

public class ScriptEvent {
    private String script;
    private String function;

    public final ScriptEntity subject;
    public final ScriptEntity object;
    public final ScriptWorld world;
    public final ScriptServer server;

    private Map<String, Object> values = new HashMap<>();

    private ActionResult resultType = ActionResult.SUCCESS;

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

    public static ScriptEvent create(Entity subject, Entity object, ServerWorld world, MinecraftServer server) {
        return create("", "", subject, object, world, server);
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setFunction(String function) {
        this.function = function;
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

    public Map<String, Object> getValues() {
        return this.values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public void setValue(String key, Object value) {
        this.values.put(key, value);
    }

    public Object getValue(String key) {
        return this.values.get(key);
    }

    public void send(String message) {
        if (this.server != null) {
            this.server.send(message);
        }
    }

    public ActionResult getResultType() {
        return this.resultType;
    }

    public void scheduleScript(String script, String function, int ticks) {
        ScriptEvent scriptEvent = this.copy();
        scriptEvent.setScript(script);
        scriptEvent.setFunction(function);

        Mappet.getExecutables().addExecutable(ticks, () -> {
            try {
                Mappet.getScripts().execute(scriptEvent);
            } catch (JavetException ignored) {
            }
        });
    }

    public void setResultType(ActionResult resultType) {
        this.resultType = resultType;
    }

    public void success() {
        this.resultType = ActionResult.SUCCESS;
    }

    public void consume() {
        this.resultType = ActionResult.CONSUME;
    }

    public void consumePartial() {
        this.resultType = ActionResult.CONSUME_PARTIAL;
    }

    public void pass() {
        this.resultType = ActionResult.PASS;
    }

    public void fail() {
        this.resultType = ActionResult.FAIL;
    }

    public ScriptEvent copy() {
        ScriptEvent scriptEvent = new ScriptEvent(this.script, this.function, this.subject, this.object, this.world, this.server);

        scriptEvent.setResultType(this.getResultType());
        scriptEvent.setValues(this.getValues());

        return scriptEvent;
    }
}
