package org.waveapi.api.entities;

import net.minecraft.entity.EntityDimensions;

public abstract class EntityBox {

    public abstract EntityDimensions getDimensions();
    public static class fixed extends EntityBox {
        public float width;
        public float height;

        public fixed(float width, float height) {
            this.width = width;
            this.height = height;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        @Override
        public EntityDimensions getDimensions() {
            return EntityDimensions.fixed(width, height);
        }
    }
}
