package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.api.triggers.RequestTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.keybinds.KeybindsSetPacket;
import com.theuran.mappet.network.packets.triggers.TriggersRequestC2SPacket;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.input.UIKeybind;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.utils.keys.KeyCombo;

public class UIKeybindOverlayPanel extends UIOverlayPanel {
    public Keybind keybind;

    public UILabel idLabel;
    public UITextbox id;
    public UILabel categoryIdLabel;
    public UITextbox categoryId;

    public UIKeybind keybindElement;

    public UIButton editTriggers;

    public UIEditorTriggersOverlayPanel panel;

    public UIKeybindOverlayPanel(Keybind keybind) {
        super(IKey.raw(keybind.id()));

        this.keybind = keybind;

        this.idLabel = new UILabel(IKey.raw("ID"));
        this.id = new UITextbox();

        this.categoryIdLabel = new UILabel(IKey.raw("CATEGORY"));
        this.categoryId = new UITextbox();
        this.keybindElement = new UIKeybind(keyCombo -> {
        });

        this.editTriggers = new UIButton(IKey.raw("Edit Triggers"), (button) -> {
            this.panel = new UIEditorTriggersOverlayPanel(RequestTrigger.KEYBINDS);

            Dispatcher.sendToServer(new TriggersRequestC2SPacket(RequestTrigger.KEYBINDS, this.id.getText()));
            UIOverlay.addOverlay(this.getContext(), this.panel, 0.55f, 0.75f).noBackground();
        });

        this.idLabel.x(15).h(20).w(0.5f, -20);
        this.categoryIdLabel.x(0.5f, 20).h(20).w(0.5f, -35);

        this.id.xy(15, 15).h(20).w(0.5f, -20);
        this.categoryId.x(0.5f, 20).y(15).h(20).w(0.5f, -35);

        this.keybindElement.xy(15, 50).w(1f, -30).h(20);
        this.keybindElement.single();

        this.editTriggers.xy(15, 80).w(1f, -30).h(20);

        this.id.relative(this.content);
        this.idLabel.relative(this.content);
        this.categoryId.relative(this.content);
        this.categoryIdLabel.relative(this.content);
        this.keybindElement.relative(this.content);
        this.editTriggers.relative(this.content);

        this.add(this.id);
        this.add(this.idLabel);
        this.add(this.categoryId);
        this.add(this.categoryIdLabel);
        this.add(this.keybindElement);
        this.add(this.editTriggers);

        this.id.setText(this.keybind.id());
        this.categoryId.setText(this.keybind.category());
        this.keybindElement.setKeyCombo(new KeyCombo(this.keybind.id(), IKey.raw(this.keybind.id()), this.keybind.keycode()));
    }

    @Override
    public void onClose() {
        super.onClose();

        Dispatcher.sendToServer(new KeybindsSetPacket(this.keybind.id(), new Keybind(
                this.id.getText(),
                this.categoryId.getText(),
                this.keybindElement.combo.getMainKey(),
                this.keybind.type(),
                this.keybind.mod()
        )));
    }
}
