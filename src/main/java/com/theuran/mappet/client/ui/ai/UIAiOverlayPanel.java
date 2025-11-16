package com.theuran.mappet.client.ui.ai;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;
import mchorse.bbs_mod.ui.utils.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.theuran.mappet.client.ai.AiMain.getAIResponse;

public class UIAiOverlayPanel extends UIOverlayPanel {
    private final UIText aiText;

    private final long MAX_DELAY = 5;

    private long delay = MAX_DELAY;

    private final List<Character> chars = new ArrayList<>();

    public UIAiOverlayPanel() {
        super(UIMappetKeys.AI_TITLE);

        this.aiText = new UIText();

        this.aiText.text("");

        Thread.startVirtualThread(() -> {
            for (char c : getAIResponse("Что такое язык программирования JavaScript?", true).toCharArray()) {
                this.chars.add(c);
            }
        });

        this.aiText.x(10);
        this.aiText.w(1f, -20);
        this.aiText.h(1f, -100);

        this.aiText.relative(this.content);

        this.add(this.aiText);
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        if (this.chars != null && !this.chars.isEmpty()) {
            if (this.delay > 0) {
                this.delay--;
            } else {
                this.aiText.text(this.aiText.getText().get() + this.chars.getFirst());
                this.delay = MAX_DELAY;
                this.chars.removeFirst();
            }
        }
    }
}