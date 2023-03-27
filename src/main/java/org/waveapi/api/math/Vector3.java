package org.waveapi.api.math;

import net.minecraft.util.math.Vec3d;

public class Vector3 {

    public Vector3(Vec3d v) {
        this.v = v;
    }

    public Vector3(double x, double y, double z) {
        this.v = new Vec3d(x, y, z);
    }

    public Vec3d v;
    public Vector3 withX(double x) {
        return new Vector3(new Vec3d(x, v.y, v.z));
    }
    public Vector3 withY(double y) {
        return new Vector3(new Vec3d(v.x, y, v.z));
    }
    public Vector3 withZ(double z) {
        return new Vector3(new Vec3d(v.x, v.y, z));
    }


    public double getX() {
        return v.x;
    }
    public double getY() {
        return v.y;
    }
    public double getZ() {
        return v.z;
    }

    public Vector3 addX(double x) {
        return new Vector3(v.add(x, 0, 0));
    }
    public Vector3 addY(double y) {
        return new Vector3(v.add(0, y, 0));
    }
    public Vector3 addZ(double z) {
        return new Vector3(v.add(0, 0, z));
    }

    public Vector3 add(Vector3 vector) {
        return new Vector3(v.add(vector.v));
    }
    public Vector3 add(double x, double y, double z) {
        return new Vector3(v.add(x, y, z));
    }

    public Vector3 subtract(double x, double y, double z) {
        return new Vector3(v.subtract(x, y, z));
    }

    public double distanceTo(Vector3 vector3) {
        return v.distanceTo(vector3.v);
    }

    public Vector3 rotateX(float angle) {
        return new Vector3(v.rotateX(angle));
    }
    public Vector3 rotateY(float angle) {
        return new Vector3(v.rotateY(angle));
    }
    public Vector3 rotateZ(float angle) {
        return new Vector3(v.rotateZ(angle));
    }

}
