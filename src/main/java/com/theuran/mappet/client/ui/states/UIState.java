package com.theuran.mappet.client.ui.states;

import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.ByteType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIToggle;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.colors.Colors;

public class UIState extends UIElement {
    private UIIcon remove;
    private UIIcon convert;
    private UITextbox id;
    private UIElement value;

    private String key;
    private MapType states;

    public UIState(String key, MapType states) {
        super();

        this.key = key;
        this.states = states;

        this.id = new UITextbox(this::rename);
        this.id.setColor(1000);
        this.id.setText(key);
        this.id.w(120);
        this.convert = new UIIcon(Icons.REFRESH, this::convert);
        this.remove = new UIIcon(Icons.REMOVE, this::removeState);

        this.row(0).preferred(2);
        this.updateValue();
    }

    private void updateValue() {
        BaseType value = this.states.get(this.key);

        if (value.isString()) {
            UITextbox element = new UITextbox((string) -> this.states.putString(this.key, string));

            element.setColor(10000);
            element.setText(value.asString());
            this.value = element;
        } else if (value instanceof ByteType value2) {
            UIToggle element = new UIToggle(IKey.EMPTY, value2.boolValue(), (toggle) -> this.states.putBool(this.key, toggle.getValue()));

            this.value = element;
        } else {
            UITrackpad element = new UITrackpad((number) -> this.states.putDouble(this.key, number));

            element.setValue(value.asNumeric().doubleValue());
            this.value = element;
        }

        this.removeAll();
        this.add(this.id, this.convert, this.value, this.remove);

        if (this.hasParent()) {
            this.removeFromParent();
        }
    }

    private void rename(String key) {
        if (this.states.elements.containsKey(key) || key.isEmpty()) {
            this.id.setColor(Colors.NEGATIVE);

            return;
        }

        this.id.setColor(0xffffff);

        BaseType value = this.states.get(this.key);

        this.states.remove(this.key);
        this.states.put(key, value);

        this.key = key;
    }

    private void removeState(UIIcon icon) {
        this.states.remove(this.key);

        UIElement parent = this.getParentContainer();

        this.removeFromParent();
        parent.resize();
    }

    public String getKey() {
        return this.key;
    }

    private void convert(UIIcon icon) {
        BaseType value = this.states.get(this.key);

        if (value.isString()) {
            this.states.putString(this.key, "");
        } else if (value instanceof ByteType value2) {
            this.states.putBool(this.key, value2.boolValue());
        } else {
            this.states.putDouble(this.key, 0);
        }

        this.updateValue();
    }
}
