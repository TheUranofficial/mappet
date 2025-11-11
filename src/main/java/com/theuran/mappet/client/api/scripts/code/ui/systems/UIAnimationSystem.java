package com.theuran.mappet.client.api.scripts.code.ui.systems;

import com.theuran.mappet.client.api.scripts.code.ui.animation.AnimationManager;
import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UIAnimationSystem {
    private final String id;
    private final UIComponent<?> component;
    private final AnimationManager animationManager;

    public UIAnimationSystem(String id, UIComponent<?> component, AnimationManager animationManager) {
        this.id = id;
        this.component = component;
        this.animationManager = animationManager;
    }

    public UIAnimationSystem afterAnimation(String interpolation, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        this.animationManager.registerAfterAnimation(this.component, this.id, interpolation, delay, consumer);
        return this;
    }

    public UIAnimationSystem afterAnimation(float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        return this.afterAnimation("linear", delay, consumer);
    }

    public UIAnimationSystem onCompleted(Consumer<UIComponent<?>> consumer) {
        this.animationManager.onCompleted(this.id, consumer);
        return this;
    }

    public void repeat(int repeatCount) {
        this.animationManager.repeat(this.id, repeatCount);
    }

    public void repeat() {
        this.animationManager.repeat(this.id, -1);
    }
}
