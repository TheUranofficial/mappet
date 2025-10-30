package com.theuran.mappet.exceptions;

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
