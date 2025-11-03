package com.theuran.mappet.api.scripts.code.bbs;

import com.theuran.mappet.api.scripts.code.ScriptFactory;
import com.theuran.mappet.api.scripts.code.nbt.ScriptNbtCompound;
import mchorse.bbs_mod.forms.forms.Form;

public class BBSForm {
    private final Form form;

    public BBSForm(Form form) {
        this.form = form;
    }

    public Form getForm() {
        return this.form;
    }

    public void setVisible(boolean visible) {
        this.form.visible.set(visible);
    }

    public boolean isVisible() {
        return this.form.visible.get();
    }

    public void setAnimatable(boolean animatable) {
        this.form.animatable.set(animatable);
    }

    public boolean isAnimatable() {
        return this.form.animatable.get();
    }

    public void setTrackName(String trackName) {
        this.form.trackName.set(trackName);
    }

    public String getTrackName() {
        return this.form.trackName.get();
    }

    public void setLightning(float lightning) {
        this.form.lighting.set(lightning);
    }

    public float getLightning() {
        return this.form.lighting.get();
    }

    public void setName(String name) {
        this.form.name.set(name);
    }

    public String getName() {
        return this.form.name.get();
    }

    public void setTransform(BBSTransform transform) {
        this.form.transform.set(transform.getTransform());
    }

    public BBSTransform getTransform() {
        return new BBSTransform(this.form.transform.get());
    }

    public void setTransformOverlay(BBSTransform transform) {
        this.form.transformOverlay.set(transform.getTransform());
    }

    public BBSTransform getTransformOverlay() {
        return new BBSTransform(this.form.transformOverlay.get());
    }

    public void setUIScale(float uiScale) {
        this.form.uiScale.set(uiScale);
    }

    public float getUIScale() {
        return this.form.uiScale.get();
    }

    public void setShaderShadow(boolean shaderShadow) {
        this.form.shaderShadow.set(shaderShadow);
    }

    public boolean isShaderShadow() {
        return this.form.shaderShadow.get();
    }

    public void setHitbox(boolean hitbox) {
        this.form.hitbox.set(hitbox);
    }

    public boolean isHitbox() {
        return this.form.hitbox.get();
    }

    public void setHitboxWidth(float hitboxWidth) {
        this.form.hitboxWidth.set(hitboxWidth);
    }

    public float getHitboxWidth() {
        return this.form.hitboxWidth.get();
    }

    public void setHitboxHeight(float hitboxHeight) {
        this.form.hitboxHeight.set(hitboxHeight);
    }

    public float getHitboxHeight() {
        return this.form.hitboxHeight.get();
    }

    public void setHitboxSneakMultiplier(float hitboxSneakMultiplier) {
        this.form.hitboxSneakMultiplier.set(hitboxSneakMultiplier);
    }

    public float getHitboxSneakMultiplier() {
        return this.form.hitboxSneakMultiplier.get();
    }

    public void setHitboxEyeHeight(float hitboxEyeHeight) {
        this.form.hitboxEyeHeight.set(hitboxEyeHeight);
    }

    public float getHitboxEyeHeight() {
        return this.form.hitboxEyeHeight.get();
    }

    public void setHp(float hp) {
        this.form.hp.set(hp);
    }

    public float getHp() {
        return this.form.hp.get();
    }

    public void setSpeed(float speed) {
        this.form.speed.set(speed);
    }

    public float getSpeed() {
        return this.form.speed.get();
    }

    public void setStepHeight(float stepHeight) {
        this.form.stepHeight.set(stepHeight);
    }

    public float getStepHeight() {
        return this.form.stepHeight.get();
    }

    public void setHotkey(int hotkey) {
        this.form.hotkey.set(hotkey);
    }

    public int getHotkey() {
        return this.form.hotkey.get();
    }

    public ScriptNbtCompound toNbt() {
        return new ScriptFactory().createCompound(form.toData().toString());
    }
}