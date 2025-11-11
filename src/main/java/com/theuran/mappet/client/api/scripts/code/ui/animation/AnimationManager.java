package com.theuran.mappet.client.api.scripts.code.ui.animation;

import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AnimationManager {
    private final ConcurrentLinkedQueue<Animation> animations = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, List<Animation>> queuedAnimations = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Consumer<UIComponent<?>>> completionCallbacks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> repeatCounts = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, List<Animation>> chainTemplates = new ConcurrentHashMap<>();

    public void registerAnimation(UIComponent<?> component, String id, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        Animation anim = new Animation(id, component, interpolation, durationSeconds, consumer);
        this.animations.add(anim);
        this.chainTemplates.computeIfAbsent(id, k -> new ArrayList<>()).add(anim);
    }

    public void registerAfterAnimation(UIComponent<?> component, String afterAnimationId, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        Animation next = new Animation(afterAnimationId, component, interpolation, durationSeconds, consumer);
        queuedAnimations.computeIfAbsent(afterAnimationId, k -> new ArrayList<>()).add(next);
        chainTemplates.computeIfAbsent(afterAnimationId, k -> new ArrayList<>()).add(next);
    }

    public void onCompleted(String animationId, Consumer<UIComponent<?>> consumer) {
        completionCallbacks.put(animationId, consumer);
    }

    public void repeat(String animationId, int times) {
        repeatCounts.put(animationId, times);
    }

    public void playAnimations() {
        List<Animation> finished = new ArrayList<>();

        for (Animation animation : animations) {
            long elapsed = System.currentTimeMillis() - animation.startTime;
            animation.progress = Math.min(Math.max((double) elapsed / animation.duration, 0), 1);
            double interpolatedProgress = animation.interpolation.interpolate(0.0d, 1.0d, animation.progress);
            animation.consumer.accept(animation.component, interpolatedProgress);

            if (animation.progress >= 1) finished.add(animation);
        }

        for (Animation animation : finished) {
            this.cancelAnimation(animation.id);

            if (queuedAnimations.containsKey(animation.id)) {
                List<Animation> nextList = queuedAnimations.remove(animation.id);
                for (Animation next : nextList) {
                    next.component = animation.component;
                    next.startTime = System.currentTimeMillis();
                    animations.add(next);
                }
            } else {
                Integer repeatLeft = repeatCounts.get(animation.id);
                if (repeatLeft != null) {
                    if (repeatLeft == -1 || repeatLeft > 1) {
                        if (repeatLeft > 1) repeatCounts.put(animation.id, repeatLeft - 1);
                        restartChain(animation.id, animation.component);
                    } else {
                        repeatCounts.remove(animation.id);
                        if (completionCallbacks.containsKey(animation.id)) {
                            completionCallbacks.get(animation.id).accept(animation.component);
                        }
                    }
                } else {
                    if (completionCallbacks.containsKey(animation.id)) {
                        completionCallbacks.get(animation.id).accept(animation.component);
                    }
                }
            }
        }
    }

    private void restartChain(String rootId, UIComponent<?> component) {
        List<Animation> original = chainTemplates.get(rootId);
        if (original == null) return;

        for (Animation template : original) {
            Animation copy = new Animation(template.id, component, template.interpolation.getKey(), template.duration / 1000f, template.consumer);
            copy.startTime = System.currentTimeMillis();
            animations.add(copy);
        }
    }

    public void cancelAnimation(String id) {
        this.animations.removeIf(animation -> animation.id.equals(id));
    }
}
