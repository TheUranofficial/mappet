package com.theuran.mappet.utils.documentation;

import java.util.List;

public record Method(String name, String description, List<Argument> arguments, String returnType) {
    public String getArgumentsString() {
        String argumentsString = "";

        for (int i = 0; i < this.arguments.size(); i++) {
            Argument argument = this.arguments.get(i);
            argumentsString += argument.type + " " + argument.name;

            if (i < this.arguments.size() - 1) {
                argumentsString += ", ";
            }
        }

        return argumentsString;
    }
}
