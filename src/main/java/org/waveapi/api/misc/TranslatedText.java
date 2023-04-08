package org.waveapi.api.misc;

import org.waveapi.api.WaveMod;
import org.waveapi.content.resources.LangManager;

import static org.waveapi.Main.bake;

public class TranslatedText extends Text {
    private final WaveMod mod;
    private final String key;

    public TranslatedText(String name, WaveMod mod) {
        super(net.minecraft.text.Text.translatable("translatable.waveAPI_text." + mod.name + "." + name));
        key = "translatable.waveAPI_text." + mod.name + "." + name;
        this.mod = mod;
    }

    public TranslatedText addTranslation(String language, String translation) {
        if (!bake)return this;
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
