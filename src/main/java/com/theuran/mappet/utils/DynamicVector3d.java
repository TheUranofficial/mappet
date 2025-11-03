package com.theuran.mappet.utils;

import mchorse.bbs_mod.utils.interps.IInterp;
import mchorse.bbs_mod.utils.interps.Interpolations;
import org.joml.Vector3d;
import org.joml.Vector4d;

public class DynamicVector3d {
    private final Vector4d from = new Vector4d();
    private final Vector4d to = new Vector4d();
    private IInterp interp = Interpolations.LINEAR;

    public void lerpTo(Vector4d from, Vector4d to, IInterp interp) {
        this.from.set(from);
        this.to.set(to);
        this.interp = interp;
    }

    public void set(double x, double y, double z) {
        this.from.set(x, y, z, 1);
        this.to.set(x, y, z, 1);
    }

    public Vector3d get() {
        double progress = Math.min(1, Math.max((System.currentTimeMillis() - this.from.w) / (this.to.w - this.from.w), 0));
        return new Vector3d(
            this.interp.interpolate(this.from.x, this.to.x, progress),
            this.interp.interpolate(this.from.y, this.to.y, progress),
            this.interp.interpolate(this.from.z, this.to.z, progress)
        );
    }

    public boolean inProgress() {
        return (System.currentTimeMillis() >= this.from.w) && (System.currentTimeMillis() <= this.to.w);
    }
}
