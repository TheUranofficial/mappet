package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.SoundTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.UIKeys;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UISoundOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIStringOverlayPanel;

public class UISoundTriggerPanel extends UIStringTriggerPanel<SoundTrigger> {
    public UISoundTriggerPanel(UIEditorTriggersOverlayPanel overlay, SoundTrigger trigger) {
        super(overlay, trigger);

        this.addPicker();
        this.addDelay();
    }

    @Override
    public IKey getLabel() {
        return UIKeys.OVERLAYS_SOUNDS_MAIN;
    }

    @Override
    protected void openOverlay() {
        UIStringOverlayPanel overlay = new UISoundOverlayPanel(link -> this.trigger.key.set(link.toString())).set(trigger.key.get());

        UIOverlay.addOverlay(this.getContext(), overlay, 0.5f, 0.9f);
    }

    @Override
    public ContentType getType() {
        return null;
    }
}
