package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.colors.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIState extends UIElement {
    public UITextbox key;
    public UITextbox value;
    public UIIcon remove;

    public UIState(String value) {
        this.remove = new UIIcon(Icons.REMOVE, this::removeState);
        this.key = new UITextbox(1000, null);
        this.value = new UITextbox(10000, this::update);
        this.value.setText(value);
        this.value.context((menu) -> {
            menu.action(Icons.REFRESH, UIMappetKeys.STATES_REFRESH, this::refreshValue);
        });

        this.row(0).preferred(2);
        this.add(this.key, UI.row(this.remove, this.value));
    }

    private void refreshValue() {
    }

    private void update(String string) {
    }

    private void removeState(UIIcon icon) {

    }

    @Override
    public void render(UIContext context) {
        super.render(context);
        context.batcher.outline(this.value.textbox.area.x, this.value.textbox.area.y, this.value.textbox.area.ex(), this.value.textbox.area.ey(), Colors.A100 + Colors.RED);
    }
}
