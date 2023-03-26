package org.waveapi.api.misc;

import net.minecraft.nbt.NbtCompound;

public class NBT {

    private final NbtCompound compound;

    public NBT(NbtCompound compound) {
        this.compound = compound;
    }

    public  void writeInt(String name, int i) {
        compound.putInt(name, i);
    }
    public  void writeFloat(String name, float f) {
        compound.putFloat(name, f);
    }
    public  void writeString(String name, String str) {
        compound.putString(name, str);
    }

    public  int readInt(String name) {
        return compound.getInt(name);
    }
    public  float readFloat(String name) {
        return compound.getFloat(name);
    }
    public  String readString(String name) {
        return compound.getString(name);
    }

}
