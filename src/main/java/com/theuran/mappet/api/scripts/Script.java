package com.theuran.mappet.api.scripts;

public class Script {
    private String name;
    private String content;

    public Script(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public void eval() {

    }
}
