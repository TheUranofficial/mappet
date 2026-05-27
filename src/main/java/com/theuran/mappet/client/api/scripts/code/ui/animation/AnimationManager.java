package com.theuran.mappet.client.api.scripts.code.ui.animation;

import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class AnimationManager {
    private ConcurrentLinkedQueue<Animation> animations = new ConcurrentLinkedQueue<>();
    private ConcurrentHashMap<String, List<Animation>> queuedAnimations = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Consumer<UIComponent<?>>> completionCallbacks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> repeatCounts = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, List<Animation>> chainTemplates = new ConcurrentHashMap<>();

    public void registerAnimation(UIComponent<?> component, String id, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        Animation anim = new Animation(id, component, interpolation, durationSeconds, consumer);

        this.animations.add(anim);
        this.chainTemplates.computeIfAbsent(id, k -> new ArrayList<>()).add(anim);
    }

    public void registerAfterAnimation(UIComponent<?> component, String afterAnimationId, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        Animation next = new Animation(afterAnimationId, component, interpolation, durationSeconds, consumer);

        this.queuedAnimations.computeIfAbsent(afterAnimationId, k -> new ArrayList<>()).add(next);
        this.chainTemplates.computeIfAbsent(afterAnimationId, k -> new ArrayList<>()).add(next);
    }

    public void onCompleted(String animationId, Consumer<UIComponent<?>> consumer) {
        this.completionCallbacks.put(animationId, consumer);
    }

    public void repeat(String animationId, int times) {
        this.repeatCounts.put(animationId, times);
    }

    public void playAnimations() {
        List<Animation> finished = new ArrayList<>();

        for (Animation animation : this.animations) {
            long elapsed = System.currentTimeMillis() - animation.startTime;

            animation.progress = Math.clamp((double) elapsed / animation.duration, 0, 1);

            double interpolatedProgress = animation.interpolation.interpolate(0.0d, 1.0d, animation.progress);

            animation.consumer.accept(animation.component, interpolatedProgress);

            if (animation.progress >= 1) {
                finished.add(animation);
            }
        }

        for (Animation animation : finished) {
            this.cancelAnimation(animation.id);

            if (this.queuedAnimations.containsKey(animation.id)) {
                List<Animation> nextList = this.queuedAnimations.remove(animation.id);

                for (Animation next : nextList) {
                    next.component = animation.component;
                    next.startTime = System.currentTimeMillis();

                    this.animations.add(next);
                }
            } else {
                Integer repeatLeft = this.repeatCounts.get(animation.id);

                if (repeatLeft != null) {
                    if (repeatLeft == -1 || repeatLeft > 1) {
                        if (repeatLeft > 1) {
                            this.repeatCounts.put(animation.id, repeatLeft - 1);
                        }

                        this.restartChain(animation.id, animation.component);
                    } else {
                        this.repeatCounts.remove(animation.id);

                        if (this.completionCallbacks.containsKey(animation.id)) {
                            this.completionCallbacks.get(animation.id).accept(animation.component);
                        }
                    }
                } else {
                    if (this.completionCallbacks.containsKey(animation.id)) {
                        this.completionCallbacks.get(animation.id).accept(animation.component);
                    }
                }
            }
        }
    }

    private void restartChain(String rootId, UIComponent<?> component) {
        List<Animation> original = this.chainTemplates.get(rootId);

        if (original == null) {
            return;
        }

        for (Animation template : original) {
            Animation copy = new Animation(template.id, component, template.interpolation.getKey(), template.duration / 1000f, template.consumer);

            copy.startTime = System.currentTimeMillis();

            this.animations.add(copy);
        }
    }

    public void cancelAnimation(String id) {
        this.animations.removeIf(animation -> animation.id.equals(id));
    }
}
