package com.theuran.mappet.utils;

import mchorse.bbs_mod.utils.interps.IInterp;
import mchorse.bbs_mod.utils.interps.Interpolations;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class DynamicVector3f {
    private final Vector4f from = new Vector4f();
    private final Vector4f to = new Vector4f();
    private IInterp interp = Interpolations.LINEAR;

    public void lerpTo(Vector4f from, Vector4f to, IInterp interp) {
        this.from.set(from);
        this.to.set(to);
        this.interp = interp;
    }

    public void set(float x, float y, float z) {
        this.from.set(x, y, z, 1);
        this.to.set(x, y, z, 1);
    }

    public Vector3f get() {
        float progress = Math.min(1, Math.max((System.currentTimeMillis() - this.from.w) / (this.to.w - this.from.w), 0));
        return new Vector3f(
            this.interp.interpolate(this.from.x, this.to.x, progress),
            this.interp.interpolate(this.from.y, this.to.y, progress),
            this.interp.interpolate(this.from.z, this.to.z, progress)
        );
    }

    public boolean inProgress() {
        return (System.currentTimeMillis() >= this.from.w) && (System.currentTimeMillis() <= this.to.w);
    }
}
