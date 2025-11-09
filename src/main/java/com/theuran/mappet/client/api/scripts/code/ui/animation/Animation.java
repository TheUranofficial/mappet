package com.theuran.mappet.client.api.scripts.code.ui.animation;

import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;
import mchorse.bbs_mod.utils.interps.IInterp;
import mchorse.bbs_mod.utils.interps.Interpolations;

import java.util.function.BiConsumer;

public class Animation {
    String id;
    UIComponent<?> component;
    IInterp interpolation;
    long startTime;
    long duration;
    double progress;
    BiConsumer<UIComponent<?>, Double> consumer;

    public Animation(String id, UIComponent<?> component, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        this.id = id;
        this.component = component;
        this.interpolation = Interpolations.get(interpolation.toLowerCase());
        this.startTime = System.currentTimeMillis();
        this.duration = (long)(durationSeconds * 1000);
        this.progress = 0;
        this.consumer = consumer;
    }
}
