package com.theuran.mappet.utils;

public class BooleanUtils {
    public static boolean isBoolean(String string) {
        return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
    }
}