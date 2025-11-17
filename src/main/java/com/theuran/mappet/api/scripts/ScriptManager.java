package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.values.V8Value;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.logger.LogType;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.scripts.ScriptsSendPacket;
import com.theuran.mappet.utils.ScriptUtils;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.manager.storage.JSONLikeStorage;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ScriptManager extends BaseManager<Script> {
    private final Map<String, Script> scripts = new ConcurrentHashMap<>();

    public ScriptManager(Supplier<File> folder) {
        super(folder);

        this.storage = new JSONLikeStorage().json();
    }

    public void initialize() {
        for (String key : this.getKeys()) {
            if (key.endsWith("/"))
                continue;

            Script script = this.load(key);

            if (script != null) {
                this.scripts.put(key, script);
            }
        }
    }

    public String eval(String content, ScriptEvent properties) throws JavetException {
        try {
            V8Runtime runtime = ScriptUtils.createRuntime();

            runtime.getGlobalObject().set("event", properties);

            V8Value value = runtime.getExecutor(content).execute();

            if (value != null)
                value.close();

            return value == null ? "null" : value.toString();
        } catch (JavetException e) {
            Mappet.getLogger().addLog(LogType.ERROR, "eval", e);
            throw e;
        }
    }

    public String execute(ScriptEvent properties) throws JavetException {
        Script script = this.getScript(properties.getScript());

        return script == null ? "null" : script.execute(properties);
    }

    public Script getScript(String id) {
        if (this.scripts.containsKey(id)) {
            return this.scripts.get(id);
        }
        return this.loadScript(id);
    }

    public void sendClientScripts(ServerPlayerEntity player) {
        Dispatcher.sendTo(new ScriptsSendPacket(this.getClientScripts()), player);
    }

    public List<Script> getClientScripts() {
        List<Script> scripts = new ArrayList<>();
        this.scripts.forEach((name, script) -> {
            if (!script.isServer()) {
                scripts.add(script);
            }
        });

        return scripts;
    }

    public void updateLoadedScript(String id, String content, boolean isServer) {
        Script script = this.getScript(id);
        if (script != null) {
            script.setServer(isServer);
            if (!script.getContent().contains(content)) {
                Script loadScript = this.load(id);

                if (loadScript != null) {
                    loadScript.setServer(isServer);
                    this.scripts.put(id, loadScript);
                }
            }
        }
    }

    public void updateLoadedScript(String id, String content) {
        if (!this.getScript(id).getContent().contains(content)) {
            this.scripts.put(id, this.load(id));
        }
    }

    public void updateLoadedScript(Script script) {
        String id = script.getId();
        if (!this.getScript(id).getContent().contains(script.getContent())) {
            this.scripts.put(id, this.load(id));
        }
    }

    public Script loadScript(String id) {
        Script script = this.load(id);

        if (script != null) {
            this.scripts.put(id, script);
        } else {
            Mappet.getLogger().addLog(LogType.ERROR, "Mappet", "Script not found: "+id);
        }

        return script;
    }

    @Override
    protected Script createData(String s, MapType mapType) {
        Script script = new Script();

        if (mapType != null) {
            script.fromData(mapType);
        }

        return script;
    }
}
