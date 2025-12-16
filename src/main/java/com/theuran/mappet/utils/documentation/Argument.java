package com.theuran.mappet.utils.documentation;

public class Argument {
    public String name;
    public String type;

    public Argument(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}
