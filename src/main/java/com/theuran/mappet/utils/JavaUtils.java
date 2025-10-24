package com.theuran.mappet.utils;

import com.caoccao.javet.annotations.V8Function;
import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.converters.IJavetConverter;
import com.caoccao.javet.values.V8Value;

public class JavaUtils {
    private V8Runtime runtime;

    public JavaUtils(V8Runtime runtime) {
        this.runtime = runtime;
    }

    @V8Function(name = "type")
    public Class<?> type(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @V8Function(name = "from")
    public V8Value from(Object object) {
        try {
            IJavetConverter converter = this.runtime.getConverter();

            converter.getConfig().setProxyMapEnabled(false);
            converter.getConfig().setProxySetEnabled(false);

            V8Value value = converter.toV8Value(this.runtime, object);

            converter.getConfig().setProxyMapEnabled(true);
            converter.getConfig().setProxySetEnabled(true);

            return value;
        } catch (JavetException e) {
            throw new RuntimeException(e);
        }
    }

    @V8Function(name = "to")
    public Object to(V8Value value) {
        try {
            return this.runtime.getConverter().toObject(value);
        } catch (JavetException e) {
            throw new RuntimeException(e);
        }
    }
}
