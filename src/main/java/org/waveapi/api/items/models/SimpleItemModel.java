package org.waveapi.api.items.models;

import org.waveapi.api.file.texture.Texture;
import org.waveapi.api.items.WaveItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SimpleItemModel extends ItemModel {

    private Texture texture;

    public SimpleItemModel (String texturePath) {
        this(new Texture(texturePath));
    }

    public SimpleItemModel(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void build(File pack, WaveItem item) {
        File model = new File(pack, "assets/" + item.getMod().name + "/models/item/" + item.getId() + ".json");

        model.getParentFile().mkdirs();

        try {
            String tPath = texture.get(pack, item.getMod(), "item/" + item.getId());
            Files.write(model.toPath(), ("{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"" + tPath + "\"\n" +
                    "  }\n" +
                    "}").getBytes());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
