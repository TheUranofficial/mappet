package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.StringTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIStringOverlayPanel;
import mchorse.bbs_mod.utils.repos.IRepository;

public abstract class UIStringTriggerPanel <T extends StringTrigger> extends UITriggerPanel<T> {
    public UIButton picker;

    public UIStringTriggerPanel(UIEditorTriggersOverlayPanel overlay, T trigger) {
        super(overlay, trigger);

        this.picker = new UIButton(this.getLabel(), button -> this.openOverlay());
    }

    public void addPicker() {
        this.add(this.picker);
    }

    public abstract IKey getLabel();

    public abstract ContentType getType();

    protected void openOverlay() {
        IRepository<?> repository = this.getType().getRepository();

        repository.requestKeys(list -> {
            UIStringOverlayPanel panel = new UIStringOverlayPanel(this.getLabel(), list, string -> this.trigger.key.set(string));

            panel.set(this.trigger.key.get());
            UIOverlay.addOverlay(this.getContext(), panel);
        });
    }
}
