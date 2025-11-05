package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;

import java.util.function.Consumer;

public class UITrackpadComponent extends UIComponent<UITrackpad>{
    public UITrackpadComponent(Consumer<Double> consumer) {
        super(new UITrackpad(consumer));
    }

    public UITrackpadComponent values(double normal, double weak, double strong) {
        this.element.values(normal, weak, strong);
        return this;
    }

    public UITrackpadComponent values(double normal) {
        this.element.values(normal);
        return this;
    }

    public UITrackpadComponent value(double value) {
        this.element.setValue(value);
        return this;
    }

    public UITrackpadComponent normal(double normal) {
        this.element.normal = normal;
        return this;
    }

    public UITrackpadComponent week(double weak) {
        this.element.weak = weak;
        return this;
    }

    public UITrackpadComponent strong(double strong) {
        this.element.strong = strong;
        return this;
    }

    public UITrackpadComponent min(double min) {
        this.element.min = min;
        return this;
    }

    public UITrackpadComponent max(double max) {
        this.element.max = max;
        return this;
    }

    public UITrackpadComponent limit(double min, double max) {
        this.element.limit(min, max);
        return this;
    }

    public UITrackpadComponent integer(boolean integer) {
        this.element.integer = integer;
        return this;
    }

    public UITrackpadComponent delayedInput(boolean delayedInput) {
        this.element.delayedInput = delayedInput;
        return this;
    }

    public UITrackpadComponent onlyNumbers(boolean onlyNumbers) {
        this.element.onlyNumbers = onlyNumbers;
        return this;
    }

    public UITrackpadComponent relative(boolean relative) {
        this.element.onlyNumbers = relative;
        return this;
    }

    public UITrackpadComponent allowCanceling(boolean allowCanceling) {
        this.element.allowCanceling = allowCanceling;
        return this;
    }

    public UITrackpadComponent forcedLabel(String forcedLabel) {
        this.element.forcedLabel(IKey.raw(forcedLabel));
        return this;
    }
}
