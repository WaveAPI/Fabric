package org.waveapi.api.entities.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.waveapi.api.entities.WaveEntityType;

public class WaveEntityRenderer {



    public void register(WaveEntityType type) {

    }

    public EntityRenderer<Entity> getRenderer(EntityRendererFactory.Context ctx) {

        return new EntityRenderer<>(ctx) {

            @Override
            public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

                super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
            }

            @Override
            public Identifier getTexture(Entity entity) {
                return null;
            }
        };
    }
}
