package org.waveapi.api.items.block.model;

import org.waveapi.api.file.texture.Texture;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.block.WaveBlock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SixSidedBlockModel extends BlockModel {
    private Texture texture;

    public SixSidedBlockModel (String texturePath) {
        this(new Texture(texturePath));
    }

    public SixSidedBlockModel(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void build(File pack, WaveItem mod) {

    }

    @Override
    public void buildBlock(File pack, WaveBlock block, boolean buildItem, boolean buildBlockState, String additional) {
        String name = block.getId() + additional;
        File model = new File(pack, "assets/" + block.getMod().name + "/models/block/" + name + ".json");
        File item = new File(pack, "assets/" + block.getMod().name + "/models/item/" + name + ".json");
        File state = new File(pack, "assets/" + block.getMod().name + "/blockstates/" + block.getId() + ".json");


        model.getParentFile().mkdirs();
        item.getParentFile().mkdirs();
        state.getParentFile().mkdirs();

        try {
            String id = this.texture.get(pack, block.getMod(), "block/" + block.getId());
            Files.write(model.toPath(), ("{\n" +
                    "  \"parent\": \"minecraft:block/cube_all\",\n" +
                    "  \"textures\": {\n" +
                    "    \"all\": \"" + id + "\"\n" +
                    "  }\n" +
                    "}").getBytes());

            if (buildItem) {
                Files.write(item.toPath(), ("{\n" +
                        "  \"parent\": \"" + block.getMod().name + ":block/" + name + "\"\n" +
                        "}").getBytes());
            }
            if (buildBlockState) {
                Files.write(state.toPath(), ("{\n" +
                        "  \"variants\": {\n" +
                        "    \"\": {\n" +
                        "      \"model\": \"" + block.getMod().name + ":block/" + name + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}").getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
