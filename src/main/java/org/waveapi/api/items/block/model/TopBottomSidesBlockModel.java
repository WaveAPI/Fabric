package org.waveapi.api.items.block.model;

import org.waveapi.api.file.texture.Texture;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.block.WaveBlock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TopBottomSidesBlockModel extends BlockModel {
    private Texture textureTop;
    private Texture textureBottom;
    private Texture textureSides;

    public TopBottomSidesBlockModel(String top, String bottom, String sides) {
        this(new Texture(top), new Texture(bottom), new Texture(sides));
    }

    public TopBottomSidesBlockModel(Texture top, Texture bottom, Texture sides) {
        this.textureTop = top;
        this.textureBottom = bottom;
        this.textureSides = sides;
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
            String idTop = this.textureTop.get(pack, block.getMod(), "block/" + block.getId() + "_top");
            String idSide = this.textureSides.get(pack, block.getMod(), "block/" + block.getId() + "_side");
            String idBottom = this.textureBottom.get(pack, block.getMod(), "block/" + block.getId() + "_bottom");
            Files.write(model.toPath(), (String.format("""
                    {
                      "parent": "minecraft:block/cube",
                      "textures": {
                        "down": "%s",
                        "east": "%s",
                        "north": "%s",
                        "particle": "%s",
                        "south": "%s",
                        "up": "%s",
                        "west": "%s"
                      }
                    }""", idBottom, idSide, idSide, idSide, idSide, idTop, idSide)).getBytes());

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
