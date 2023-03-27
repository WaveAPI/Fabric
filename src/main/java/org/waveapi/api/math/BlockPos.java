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

    public BlockPos setX(int x) {
        return this;
    }
    public BlockPos setY(int y) {
        return this;
    }
    public BlockPos setZ(int z) {
        return this;
    }

    public BlockPos addX(int x) {return new BlockPos(pos.add(x, 0, 0));}
    public BlockPos addY(int y) {return new BlockPos(pos.add(0, y, 0));}
    public BlockPos addZ(int z){return new BlockPos(pos.add(0, 0, z));}

    public BlockPos add(int x, int y, int z) {return new BlockPos(pos.add(x, y, z));}

    public BlockPos add(BlockPos pos) {return new BlockPos(this.pos.add(pos.pos));}



    public Vector3 toVector3() {return new Vector3(getX(), getY(), getZ());}
}
