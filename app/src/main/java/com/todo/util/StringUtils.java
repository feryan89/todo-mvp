package com.todo.util;

public class StringUtils {

    public static final String SPACE = " ";
    public static final String EMPTY = "";

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    private StringUtils() {
    }


}
