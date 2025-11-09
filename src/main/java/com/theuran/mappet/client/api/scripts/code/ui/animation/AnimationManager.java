package com.theuran.mappet.client.api.scripts.code.ui.animation;

import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

public class AnimationManager {
    private final ConcurrentLinkedQueue<Animation> animations = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, List<Animation>> queuedAnimations = new ConcurrentHashMap<>();

    public void registerAnimation(UIComponent<?> component, String id, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        this.animations.add(new Animation(id, component, interpolation, durationSeconds, consumer));
    }

    public void registerAfterAnimation(String afterAnimationId, UIComponent<?> component, String id, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        Animation nextAnimation = new Animation(id, component, interpolation, durationSeconds, consumer);
        queuedAnimations.computeIfAbsent(afterAnimationId, k -> new ArrayList<>()).add(nextAnimation);
    }

    public void playAnimations() {
        for (Animation animation : animations) {
            long elapsed = System.currentTimeMillis() - animation.startTime;
            animation.progress = Math.min(Math.max((double)elapsed / animation.duration, 0), 1);

            double interpolatedProgress = animation.interpolation.interpolate(0.0, 1.0, animation.progress);
            animation.consumer.accept(animation.component, interpolatedProgress);

            if (animation.progress >= 1) {
                this.cancelAnimation(animation.id);

                if (this.queuedAnimations.containsKey(animation.id)) {
                    List<Animation> queued = queuedAnimations.remove(animation.id);
                    for (Animation nextAnim : queued) {
                        nextAnim.component = animation.component;
                        nextAnim.startTime = System.currentTimeMillis();
                        animations.add(nextAnim);
                    }
                }
            }
        }
    }

    public void cancelAnimation(String id) {
        this.animations.removeIf(animation -> animation.id.equals(id));
    }
}