package com.theuran.mappet.client.ui.utils.treeSitter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum TSNodeType {
    PROGRAM(),
    FUNCTION_DECLARATION(),
    IDENTIFIER(),
    FORMAL_PARAMETERS(),
    STATEMENT_BLOCK(),
    EXPRESSION_STATEMENT(),
    MEMBER_EXPRESSION(),
    CALL_EXPRESSION(),
    PROPERTY_IDENTIFIER(),
    ERROR(),
    DOT(".");

    public String name;

    TSNodeType(String name) {
        this.name = name;
    }

    TSNodeType() {
        this.name = this.name().toLowerCase();
    }

    public String getName() {
        return this.name;
    }
}
