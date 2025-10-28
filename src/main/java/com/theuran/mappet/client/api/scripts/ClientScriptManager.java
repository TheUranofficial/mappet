package com.theuran.mappet.client.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientScriptManager {
    public Map<String, Script> scripts = new HashMap<>();

    public void addScripts(List<Script> scripts) {
        for (Script script : scripts) {
            this.scripts.put(script.getId(), script);
        }
    }

    public String execute(ClientScriptEvent properties) throws JavetException {
        Script script = this.getScript(properties.getScript());

        return script == null ? "null" : script.execute(properties);
    }

    public Script getScript(String id) {
        if (this.scripts.containsKey(id)) {
            return this.scripts.get(id);
        }
        return null;
    }
}