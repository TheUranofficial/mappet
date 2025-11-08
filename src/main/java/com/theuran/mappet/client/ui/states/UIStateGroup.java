package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.api.states.States;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.colors.Colors;

import java.util.Comparator;

public class UIStateGroup extends UIElement {
    private States states;
    private boolean visible = true;
    private final IKey key;
    private int last;

    public UIStateGroup(IKey key, States states) {
        this.key = key;

        this.context(menu ->
                menu.action(Icons.ADD, UIMappetKeys.STATES_ADD, this::addNew)
        );

        this.set(states);

        this.column().vertical().stretch().padding(8);
        this.h(20);
    }

    @Override
    protected boolean subMouseClicked(UIContext context) {
        if (this.area.isInside(context) && context.mouseButton == 0) {
            int x = context.mouseX - this.area.x;
            int y = context.mouseY - this.area.y - 20;

            if (y < 0) {
                if (x < this.area.x + 80) {
                    this.setValuesVisible(!this.visible);
                    return true;
                }

                return super.subMouseClicked(context);
            }
        }

        return super.subMouseClicked(context);
    }

    public void setValuesVisible(boolean flag) {
        this.visible = flag;

        for (UIState child : this.getChildren(UIState.class)) {
            child.setVisible(this.visible);
        }
    }

    public void addNew() {
        if (this.states == null)
            return;

        int index = this.states.size();
        String key;

        do {
            index += 1;
            key = "state_" + index;
        } while (this.states.has(key));

        this.states.setString(key, "");
        this.add(new UIState(key, this.states));

        this.setValuesVisible(true);

        this.getChildren().sort(Comparator.comparing(state -> ((UIState) state).getKey()));
        this.getParentContainer().resize();
    }

    public UIStateGroup set(States states) {
        this.states = states;

        this.removeAll();

        //йоу че каво а я так умею питухон стайл рулит
        if (states != null)
            for (String key : states.keys())
                this.add(new UIState(key, states));

        this.getChildren().sort(Comparator.comparing(state -> ((UIState) state).getKey()));
        this.resize();

        return this;
    }

    @Override
    public void render(UIContext context) {
        context.batcher.text(this.key.get(), this.area.x + 20, this.area.y + 4, Colors.A100 + Colors.LIGHTEST_GRAY);

        if (this.visible) {
            context.batcher.outline(this.area.x, this.area.y, this.area.ex(), this.area.ey() + 4, Colors.A100 + Colors.ORANGE);
            context.batcher.icon(Icons.MOVE_DOWN, (float) (this.area.x + 10), (float) (this.area.y + 4), 0.5F, 0.0F);
            super.render(context);
        } else {
            context.batcher.icon(Icons.MOVE_UP, (float) (this.area.x + 10), (float) (this.area.y + 4), 0.5F, 0.0F);
            context.batcher.outline(this.area.x, this.area.y, this.area.ex(), this.area.y + 15, Colors.A100 + Colors.ORANGE);
        }

        int h = 20;

        if (this.visible) {
            h = this.area.h;
        }

        if (this.last != h) {
            this.last = h;

            UIElement parent = this.getParentContainer();

            if (parent != null) {
                this.h(h);
                parent.resize();
            }
        }
    }
}
