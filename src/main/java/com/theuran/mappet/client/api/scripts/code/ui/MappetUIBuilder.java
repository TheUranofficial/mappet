package com.theuran.mappet.client.api.scripts.code.ui;

import com.theuran.mappet.client.api.scripts.code.ui.animation.AnimationManager;
import com.theuran.mappet.client.api.scripts.code.ui.components.*;
import com.theuran.mappet.client.api.scripts.code.ui.systems.UIAnimationSystem;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.icons.Icon;
import mchorse.bbs_mod.ui.utils.icons.Icons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MappetUIBuilder {
    public List<UIComponent<?>> components = new ArrayList<>();
    public AnimationManager animationManager = new AnimationManager();

    public UIAnimationSystem animation(String componentId, String id, String interpolation, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        UIComponent<?> component = this.getComponent(componentId);

        UIAnimationSystem animationSystem = new UIAnimationSystem(id, component, this.animationManager);

        this.animationManager.registerAnimation(component, id, interpolation, delay, consumer);

        return animationSystem;
    }

    public UIAnimationSystem animation(String componentId, String id, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        return this.animation(componentId, id, "linear", delay, consumer);
    }

    public UIAnimationSystem animation(String componentId, float delay, BiConsumer<UIComponent<?>, Double> consumer) {
        return this.animation(componentId, UUID.randomUUID().toString(), delay, consumer);
    }

    public void cancelAnimation(String animationId) {
        this.animationManager.cancelAnimation(animationId);
    }

    public UIComponent<?> getComponent(String id) {
        for (UIComponent<?> component : this.components) {
            if (component.getMappetElement().getUndoId().equals(id)) {
                return component;
            }
        }

        return null;
    }

    public UIButtonComponent button() {
        return this.button("", null);
    }

    public UIButtonComponent button(String label) {
        return this.button(label, null);
    }

    public UIButtonComponent button(String label, Runnable onClick) {
        UIButtonComponent button = new UIButtonComponent(label, onClick);
        this.components.add(button);
        return button;
    }

    public UILabelComponent label(String text, int color) {
        UILabelComponent label = new UILabelComponent(text, color);
        this.components.add(label);
        return label;
    }

    public UILabelComponent label(String text) {
        return this.label(text, -1);
    }

    public UIToggleComponent toggle(String label, boolean value, Runnable runnable) {
        UIToggleComponent toggle = new UIToggleComponent(label, value, runnable);
        this.components.add(toggle);
        return toggle;
    }

    public UIToggleComponent toggle(String label, boolean value) {
        return this.toggle(label, value, null);
    }

    public UIToggleComponent toggle(String label) {
        return this.toggle(label, false, null);
    }

    public UITextboxComponent textbox() {
        return this.textbox(10000, null);
    }

    public UITextboxComponent textbox(int maxLength) {
        return this.textbox(maxLength, null);
    }

    public UITextboxComponent textbox(int maxLength, Consumer<String> consumer) {
        return this.textbox("", maxLength, consumer);
    }

    public UITextboxComponent textbox(String text) {
        return this.textbox(text, 10000, null);
    }

    public UITextboxComponent textbox(String text, int maxLength) {
        return this.textbox(text, maxLength, null);
    }

    public UITextboxComponent textbox(String text, Consumer<String> consumer) {
        return this.textbox(text, 10000, consumer);
    }

    public UITextboxComponent textbox(String text, int maxLength, Consumer<String> consumer) {
        UITextboxComponent textbox = new UITextboxComponent(maxLength, consumer);
        textbox.text(text);
        this.components.add(textbox);
        return textbox;
    }

    public UIIconComponent icon(String iconId, Runnable onClick) {
        Icon icon = Icons.ICONS.get(iconId.toLowerCase());

        if (icon == null) {
            icon = Icons.HELP;
        }

        UIIconComponent iconComponent = new UIIconComponent(icon, onClick);
        this.components.add(iconComponent);
        return iconComponent;
    }

    public UIIconComponent icon(String iconId) {
        return this.icon(iconId, null);
    }

    public List<UIIconComponent> icons() {
        List<UIIconComponent> icons = new ArrayList<>();
        for (String iconId : Icons.ICONS.keySet()) {
            icons.add(icon(iconId));
        }
        return icons;
    }

    public UITrackpadComponent trackpad(Consumer<Double> consumer) {
        UITrackpadComponent trackpad = new UITrackpadComponent(consumer);
        this.components.add(trackpad);
        return trackpad;
    }

    public UITrackpadComponent trackpad() {
        return this.trackpad(null);
    }

    public UIOverlayComponent overlay(String title, Consumer<MappetUIBuilder> consumer) {
        UIOverlayComponent overlay = new UIOverlayComponent(title, consumer);
        this.components.add(overlay);
        return overlay;
    }

    public void openOverlay(UIOverlayComponent overlay) {
//        UIOverlayPanel overlayPanel = new UIOverlayPanel(IKey.raw(title));
//
//        for (UIComponent<?> child : this.components) {
//            overlayPanel.add(child.getMappetElement().relative(overlayPanel));
//        }
//
//        UIOverlay.addOverlay(this.element.getContext(), overlayPanel, 0.55f, 0.75f);
    }

    public UILayoutComponent layout(BiConsumer<UILayoutComponent, MappetUIBuilder> consumer) {
        UILayoutComponent layout = new UILayoutComponent(consumer);
        this.components.add(layout);
        return layout;
    }

    public UITextareaComponent textarea(Consumer<String> consumer) {
        UITextareaComponent textarea = new UITextareaComponent(consumer);
        this.components.add(textarea);
        return textarea;
    }

    public UITextareaComponent textarea(String text) {
        UITextareaComponent textarea = new UITextareaComponent(null);
        textarea.text(text);
        this.components.add(textarea);
        return textarea;
    }

    public UIFormComponent form() {
        UIFormComponent form = new UIFormComponent();
        this.components.add(form);
        return form;
    }

    public UIGraphicsComponent graphics() {
        UIGraphicsComponent graphics = new UIGraphicsComponent();
        this.components.add(graphics);
        return graphics;
    }
}