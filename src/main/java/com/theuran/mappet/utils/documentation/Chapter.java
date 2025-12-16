package com.theuran.mappet.utils.documentation;

import java.util.ArrayList;
import java.util.List;

public record Chapter(String name, String description, List<Method> methods) {
    public Method getMethod(String methodName) {
        for (Method method : this.methods) {
            if (method.name().equals(methodName)) {
                return method;
            }
        }

        return null;
    }

    public List<String> getMethodNames() {
        List<String> names = new ArrayList<>();

        for (Method method : this.methods) {
            names.add(method.name());
        }

        return names;
    }

    public List<String> getMethodStrings() {
        List<String> strings = new ArrayList<>();

        for (Method method : this.methods) {
            strings.add(method.name()+"("+method.getArgumentsString()+")");
        }

        return strings;
    }
}
