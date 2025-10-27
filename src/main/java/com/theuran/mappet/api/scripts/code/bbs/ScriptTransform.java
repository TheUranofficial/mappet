package com.theuran.mappet.api.scripts.code.bbs;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import mchorse.bbs_mod.utils.pose.Transform;
import org.joml.Vector3f;

public class ScriptTransform {
    private final Transform transform;

    public ScriptTransform(Transform transform) {
        this.transform = transform;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void setScale(float x, float y, float z) {
        transform.scale.set(new Vector3f(x, y, z));
    }

    public void setScale(ScriptVector vector) {
        this.setScale((float)vector.x, (float)vector.y, (float)vector.z);
    }

    public ScriptVector getScale() {
        return new ScriptVector(transform.scale);
    }

    public void setTranslate(float x, float y, float z) {
        transform.translate.set(new Vector3f(x, y, z));
    }

    public void setTranslate(ScriptVector vector) {
        this.setTranslate((float) vector.x, (float) vector.y, (float) vector.z);
    }

    public ScriptVector getTranslate() {
        return new ScriptVector(transform.translate);
    }

    public void setRotate(float x, float y, float z) {
        transform.rotate.set(new Vector3f(x, y, z));
    }

    public void setRotate(ScriptVector vector) {
        this.setRotate((float) vector.x, (float) vector.y, (float) vector.z);
    }

    public ScriptVector getRotate() {
        return new ScriptVector(transform.rotate);
    }

    public void setRotate2(float x, float y, float z) {
        transform.rotate2.set(new Vector3f(x, y, z));
    }

    public void setRotate2(ScriptVector vector) {
        this.setRotate((float) vector.x, (float) vector.y, (float) vector.z);
    }

    public ScriptVector getRotate2() {
        return new ScriptVector(transform.rotate2);
    }

    public String toString() {
        return this.transform.toData().toString();
    }
}
