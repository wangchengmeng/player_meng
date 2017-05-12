package com.suyou.media.util;

public class UncheckedUtil {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
