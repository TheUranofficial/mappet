package com.theuran.mappet.utils;

public class MappetException extends Exception {

    public final String sourceName;

    public MappetException(String message, String sourceName) {
        super(message);
        this.sourceName = sourceName;
    }

    public MappetException(String message) {
        super(message);
        this.sourceName = "";
    }
}
