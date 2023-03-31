package org.waveapi.api.misc;

public class Text {

    public Text (net.minecraft.text.Text text) {
        this.text = text;
    }
    public final net.minecraft.text.Text text;
    public String getText() {
        return text.getString();
    }

    public static Text plain(String text) {
        return new Text(net.minecraft.text.Text.of(text));
    }
    public static Text translatable(String text) {
        return new Text(net.minecraft.text.Text.translatable(text));
    }

}
