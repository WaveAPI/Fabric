package org.waveapi.utils;

public class KotlinCheck {
    public static boolean isKotlinAvailable() {
        try {
            KotlinCheck.class.getClassLoader().loadClass("kotlin.KotlinVersion");
        } catch (Exception ignored) {
            return false;
        }

        return true;
    }
}
