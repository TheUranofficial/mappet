package com.theuran.mappet.client.ui.utils;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIPromptOverlayPanel;
import mchorse.bbs_mod.ui.utils.icons.Icons;

public class UIMappetUtils {
    public static UITextbox fullWindowContext(UITextbox text, IKey title) {
        text.context(menu -> {
            menu.action(Icons.EDIT, UIMappetKeys.OVERLAYS_FULLSCREEN, () -> {
                UIPromptOverlayPanel panel = new UIPromptOverlayPanel(title, IKey.EMPTY, value -> {
                    text.setText(value);

                    if (text.callback != null) {
                        text.callback.accept(value);
                    }
                });

                UIOverlay.addOverlay(text.getContext(), panel);
            });
        });

        return text;
    }
}
