package org.waveapi.content.resources;

import net.minecraft.resource.*;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ResourceProvider implements ResourcePackProvider {

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        profileAdder.accept(ResourcePackProfile.create("waveapi",
                Text.of("WaveAPI resources"),
                true,
                (f) -> new DirectoryResourcePack("WaveAPI", ResourcePackManager.getInstance().getPackDir().toPath(), true),
                ResourceType.CLIENT_RESOURCES,
                ResourcePackProfile.InsertionPosition.BOTTOM,
                ResourcePackSource.BUILTIN));
    }
}
