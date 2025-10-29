package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.api.states.States;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.ByteType;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIToggle;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.colors.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIState extends UIElement {
    public UITextbox id;
    public UIElement value;
    public UIIcon remove;

    private String key;
    private States states;
    private int color;

    public UIState(String key, States states) {
        this.key = key;
        this.states = states;

        this.remove = new UIIcon(Icons.REMOVE, this::removeState);
        this.id = new UITextbox(1000, this::rename).background(false);
        this.id.w(120);
        this.id.setText(key);

        this.column(0).vertical().stretch();
        this.refresh();
    }

    public String getKey() {
        return this.key;
    }

    public States getStates() {
        return this.states;
    }

    private void rename(String key) {
        if (this.states.values.has(key) || key.isEmpty()) {
            this.id.setColor(Colors.NEGATIVE);

            return;
        }

        this.id.setColor(Colors.WHITE);

        BaseType type = this.states.get(this.key);

        this.states.remove(this.key);

        this.states.set(key, type);
        this.key = key;
    }

    private void convert() {
        BaseType type = this.states.get(this.key);

        if (type.isString())
            this.states.setBoolean(this.key, false);
        else if (type instanceof ByteType)
            this.states.setNumber(this.key, 0);
        else
            this.states.setString(this.key, "");

        this.refresh();
    }

    private void removeState(UIIcon icon) {
        this.states.remove(this.key);

        UIElement parent = this.getParentContainer();

        this.removeFromParent();
        parent.resize();
    }

    private void refresh() {
        BaseType type = this.states.get(this.key);

        if (type.isString()) {
            UITextbox element = new UITextbox(10000, value -> this.states.setString(this.key, value));

            element.setText(type.asString());
            this.value = element;
            this.color = Colors.RED;
        } else if (type instanceof ByteType bool) {
            UIToggle element = new UIToggle(IKey.EMPTY, bool.boolValue(), value -> this.states.setBoolean(this.key, value.getValue()));

            this.value = element;
            this.color = element.getValue() ? 0x2A914E : 0x8B0000;
        } else {
            UITrackpad element = new UITrackpad(value -> this.states.setNumber(this.key, value));

            element.setValue(type.asNumeric().doubleValue());
            this.value = element;
            this.color = Colors.YELLOW;
        }

        this.value.context((menu) -> {
            menu.action(Icons.REFRESH, UIMappetKeys.STATES_REFRESH, this::convert);
        });

        this.removeAll();
        this.add(this.id, UI.row(this.remove, this.value));

        if (this.hasParent()) {
            this.getParentContainer().resize();
        }
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        int color = this.value instanceof UIToggle bool ? bool.getValue() ? Colors.GREEN : 0x8B0000 : this.color;

        context.batcher.outline(this.value.area.x, this.value.area.y, this.value.area.ex(), this.value.area.ey(), Colors.A100 + color);
    }
}
