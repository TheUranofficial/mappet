package com.theuran.mappet.utils;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.converters.JavetProxyConverter;

public class ScriptUtils {
    public static V8Runtime createRuntime() {
        try {
            V8Runtime runtime = V8Host.getV8Instance().createV8Runtime();
            JavetProxyConverter converter = new JavetProxyConverter();

            converter.getConfig().setProxyMapEnabled(true);
            converter.getConfig().setProxySetEnabled(true);
            converter.getConfig().setMaxDepth(24);

            runtime.setConverter(converter);

            runtime.getGlobalObject().set("Java", new JavaUtils(runtime));

            return runtime;
        } catch (JavetException e) {
            throw new RuntimeException(e);
        }
    }
}
