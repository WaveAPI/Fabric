package org.waveapi.utils;

import kotlin.KotlinVersion;

public class KotlinCheck {
    public static boolean isKotlinAvailable() {
        try {
            KotlinVersion v = KotlinVersion.CURRENT;
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }
}
