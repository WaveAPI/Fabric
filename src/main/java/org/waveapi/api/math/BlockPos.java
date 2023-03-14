package org.waveapi.api.math;

public class BlockPos {
    public final net.minecraft.util.math.BlockPos.Mutable pos;

    public BlockPos(int x, int y, int z) {
        pos = new net.minecraft.util.math.BlockPos.Mutable(x, y, z);
    }

    public BlockPos(Vector3 vector3) {
        pos = new net.minecraft.util.math.BlockPos.Mutable(vector3.getX(), vector3.getY(), vector3.getZ());
    }

    public BlockPos(net.minecraft.util.math.BlockPos.Mutable pos) {
        this.pos = pos;
    }

    public BlockPos(net.minecraft.util.math.BlockPos pos) {
        this(pos.mutableCopy());
    }

    public int getX() {
        return pos.getX();
    }
    public int getY() {
        return pos.getY();
    }
    public int getZ() {
        return pos.getZ();
    }

    public void setX(int x) {
        pos.setX(x);
    }
    public void setY(int y) {
        pos.setY(y);
    }
    public void setZ(int z) {
        pos.setZ(z);
    }
}
