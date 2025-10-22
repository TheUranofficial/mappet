package com.theuran.mappet.api.scripts;


import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import java.util.ArrayList;
import java.util.List;

public class ScriptManager {
    List<Script> scripts = new ArrayList<>();

    public ScriptManager() {

    }

    public Script getScript(String name) {
        for (Script script : this.scripts) {
            if (script.getName().equals(name)) {
                return script;
            }
        }

        return null;
    }

    public String evalCode(String content) {
        try (Context context = Context.newBuilder("js")
                .option("js.ecmascript-version", "2023")
                .allowHostAccess(HostAccess.ALL)
                .build()) {

            return context.eval("js", content).toString();
        }
    }
}
