package com.theuran.mappet.utils;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.callback.IV8ModuleResolver;
import com.caoccao.javet.interop.executors.IV8Executor;
import com.caoccao.javet.values.reference.IV8Module;
import com.caoccao.javet.values.reference.V8Module;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.Script;

public class ModuleResolver implements IV8ModuleResolver {
    @Override
    public IV8Module resolve(V8Runtime runtime, String resourceName, IV8Module refferModule) throws JavetException {
        Script script = Mappet.getScripts().getScript(resourceName);

        IV8Executor executor = runtime.getExecutor(script.getContent()).setResourceName(resourceName).setModule(true);

        try (V8Module module = executor.compileV8Module()) {
            module.executeVoid();
            module.evaluate();

            if (runtime.containsV8Module(resourceName)) {
                System.out.println(resourceName + " module is registered");
            }

            return module;
        }
    }
}
