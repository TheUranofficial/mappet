package com.theuran.mappet.utils;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptUtils {
    private static ScriptEngineManager manager;

    public static ScriptEngine getEngineByExtension(String extension) {
        extension = extension.replace(".", "");

        return getManager().getEngineByExtension(extension);
    }

    private static ScriptEngineManager getManager() {
        if (manager == null)
            manager = new ScriptEngineManager();

        return manager;
    }

    public static void sanitize(ScriptEngine engine) {
        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

        bindings.remove("load");
        bindings.remove("loadWithNewGlobal");
        bindings.remove("exit");
        bindings.remove("quit");
    }
}
