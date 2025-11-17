package com.theuran.mappet.client.api.scripts.code;

import com.theuran.mappet.api.scripts.code.entity.ScriptPlayer;
import com.theuran.mappet.client.api.scripts.code.entity.ClientScriptEntity;
import com.theuran.mappet.client.api.scripts.code.entity.ClientScriptPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.HashMap;
import java.util.Map;

public class ClientScriptEvent {
    private String script;
    private String function;

    public final ClientScriptEntity subject;
    public final ClientScriptEntity object;
    public final ClientScriptWorld world;

    private Map<String, Object> values = new HashMap<>();

    private ActionResult resultType = ActionResult.PASS;

    public ClientScriptEvent(String script, String function, ClientScriptEntity subject, ClientScriptEntity object, ClientScriptWorld world) {
        this.script = script;
        this.function = function;

        this.subject = subject;
        this.object = object;
        this.world = world;
    }

    public static ClientScriptEvent create(String script, String function, Entity subject, Entity object, ClientWorld world) {
        return new ClientScriptEvent(
                script,
                function,
                ClientScriptEntity.create(subject),
                ClientScriptEntity.create(object),
                world == null ? null : new ClientScriptWorld(world)
        );
    }

    public static ClientScriptEvent create(Entity subject, Entity object, ClientWorld world) {
        return create("", "", subject, object, world);
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

    public ClientScriptEntity getSubject() {
        return this.subject;
    }

    public ClientScriptEntity getObject() {
        return this.object;
    }

    public ClientScriptPlayer getPlayer() {
        if (this.subject instanceof ClientScriptPlayer) {
            return (ClientScriptPlayer) this.subject;
        }

        if (this.object instanceof ClientScriptPlayer) {
            return (ClientScriptPlayer) this.object;
        }

        return null;
    }

    public ClientScriptWorld getWorld() {
        return this.world;
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

    public ActionResult getResultType() {
        return this.resultType;
    }

    public void scheduleScript(String script, String function, int ticks) {
        //SCHEDULE CLIENTS
        //Mappet.getExecutables().addExecutable(ticks, script, function, this);
    }

    public void setResultType(ActionResult resultType) {
        this.resultType = resultType;
    }

    public void send(Object message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(message.toString()));
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
}
