package org.waveapi.content.resources;

import net.minecraft.resource.*;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ResourceProvider implements ResourcePackProvider {
    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder, ResourcePackProfile.Factory factory) {
        ResourcePackProfile p = new ResourcePackProfile("waveapi",
                true,
                () -> new DirectoryResourcePack(ResourcePackManager.getInstance().getPackDir()),
                Text.of("WaveAPI resources"),
                Text.of("Resources for WaveAPI mods"),
                ResourcePackCompatibility.COMPATIBLE,
                ResourcePackProfile.InsertionPosition.BOTTOM,
                true,
                ResourcePackSource.PACK_SOURCE_BUILTIN);

                profileAdder.accept(p);
    }
}
