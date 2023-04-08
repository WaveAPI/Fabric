package org.waveapi.api.misc;

import org.waveapi.api.WaveMod;
import org.waveapi.content.resources.LangManager;

public class TranslatedText extends Text {

    private static int id = 0;
    private final WaveMod mod;
    private String key;

    public TranslatedText(String name, WaveMod mod) {
        super(net.minecraft.text.Text.translatable("translatable." + id + ".waveAPI_text." + mod.name + "." + name));
        key = "translatable." + id + ".waveAPI_text." + mod.name + "." + name;
        id += 1;
        this.mod = mod;
    }

    public TranslatedText addTranslation(String language, String translation) {
        LangManager.addTranslation(mod.name, language, key, translation);
        return this;
    }

    /**
     * Replaces % in translation with objects
     */
    public Text withValues(Object... objects) {
        return new Text(net.minecraft.text.Text.translatable(key, objects));
    }

}
