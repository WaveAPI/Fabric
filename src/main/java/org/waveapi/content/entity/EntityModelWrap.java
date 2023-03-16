package org.waveapi.content.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import java.util.List;

public class EntityModelWrap<T extends Entity> extends EntityModel<Entity> {

    public final int textureSizeX;
    public final int textureSizeY;

    public final List<ModelPart> parts;
    public EntityModelWrap(int textureSizeX, int textureSizeY, List<ModelPart> parts) {
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
        this.parts = parts;
    }

    public TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-6F, 12F, -6F, 12F, 12F, 12F), ModelTransform.pivot(0F, 0F, 0F));
        return TexturedModelData.of(modelData, textureSizeX, textureSizeY);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        for (ModelPart part : parts) {
            part.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }
}
