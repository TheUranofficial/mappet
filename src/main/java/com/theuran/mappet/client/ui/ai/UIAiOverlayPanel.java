package com.theuran.mappet.client.ui.ai;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;
import mchorse.bbs_mod.ui.utils.UI;

import static com.theuran.mappet.client.ai.AiMain.getAIResponse;

public class UIAiOverlayPanel extends UIOverlayPanel {
    private final UIText aiText;

    public UIAiOverlayPanel() {
        super(UIMappetKeys.AI_TITLE);

        this.aiText = new UIText();

        this.aiText.text("");

        Thread.startVirtualThread(() -> {
            this.aiText.text(getAIResponse("Что такое mappet mod", true));
        });

        this.aiText.x(10);
        this.aiText.w(1f, -20);
        this.aiText.h(1f, -100);

        this.aiText.relative(this.content);

        this.add(this.aiText);
    }
}