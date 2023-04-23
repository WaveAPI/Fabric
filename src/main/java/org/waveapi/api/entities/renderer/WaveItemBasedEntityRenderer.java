package org.waveapi.api.entities.renderer;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import org.waveapi.api.entities.WaveEntityType;

public class WaveItemBasedEntityRenderer extends WaveEntityRenderer {



    public void register(WaveEntityType type) {

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public EntityRenderer<Entity> getRenderer(EntityRendererFactory.Context ctx) {

        return new FlyingItemEntityRenderer(ctx) {

        };
    }
}
