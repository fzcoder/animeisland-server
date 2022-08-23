package com.fzcoder.opensource.animeisland.util;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/21 3:42
 */
public class Strings {
    public static final String Val_EMPTY = "";
    public static final String Val_ALL = "all";

    public static boolean isEqualEmpty(String s) {
        return Val_EMPTY.equals(s);
    }

    public static boolean isNotEqualEmpty(String s) {
        return !isEqualEmpty(s);
    }

    public static boolean isEqualAll(String s) {
        return Val_ALL.equals(s);
    }

    public static boolean isNotEqualAll(String s) {
        return !isEqualAll(s);
    }
}
