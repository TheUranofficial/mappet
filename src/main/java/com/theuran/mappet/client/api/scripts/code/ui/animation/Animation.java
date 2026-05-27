package com.theuran.mappet.client.api.scripts.code.ui.animation;

import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;
import mchorse.bbs_mod.utils.interps.IInterp;
import mchorse.bbs_mod.utils.interps.Interpolations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public class Animation {
    public String id;
    public UIComponent<?> component;
    public IInterp interpolation;
    public long startTime = System.currentTimeMillis();
    public long duration;
    public double progress = 0;
    public BiConsumer<UIComponent<?>, Double> consumer;

    public Animation(String id, UIComponent<?> component, String interpolation, float durationSeconds, BiConsumer<UIComponent<?>, Double> consumer) {
        this.id = id;
        this.component = component;
        this.interpolation = Interpolations.get(interpolation.toLowerCase());
        this.duration = (long) durationSeconds * 1000;
        this.consumer = consumer;
    }
}
