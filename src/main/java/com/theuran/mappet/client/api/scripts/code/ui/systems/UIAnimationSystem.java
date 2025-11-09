package com.theuran.mappet.client.api.scripts.code.ui.systems;

import com.theuran.mappet.client.api.scripts.code.ui.animation.AnimationManager;
import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;

import java.util.UUID;
import java.util.function.BiConsumer;

public class UIAnimationSystem {
    private String id;
    private final UIComponent<?> component;
    private final AnimationManager animationManager;

    public UIAnimationSystem(String id, UIComponent<?> component, AnimationManager animationManager) {
        this.id = id;
        this.component = component;
        this.animationManager = animationManager;
    }

    public UIAnimationSystem animation(String animationId, String interpolation, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        this.animationManager.registerAnimation(this.component, animationId, interpolation, delay, consumer);
        this.id = animationId;
        return this;
    }

    public UIAnimationSystem animation(String interpolation, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        this.animationManager.registerAnimation(this.component, this.id, interpolation, delay, consumer);
        return this;
    }

    public UIAnimationSystem afterAnimation(String animationId, String interpolation, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        this.animationManager.registerAfterAnimation(this.id, this.component, animationId, interpolation, delay, consumer);
        this.id = animationId;
        return this;
    }

    public UIAnimationSystem afterAnimation(String animationId, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        return this.afterAnimation(animationId, "linear", delay, consumer);
    }

    public UIAnimationSystem afterAnimation(float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        return this.afterAnimation(UUID.randomUUID().toString(), delay, consumer);
    }
}
