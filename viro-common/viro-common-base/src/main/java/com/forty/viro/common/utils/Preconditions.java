package com.forty.viro.common.utils;

public class Preconditions {

    public static void checkArgument(boolean expression, String message) {
        if (!expression) { throw new IllegalArgumentException(message); }
    }
}
