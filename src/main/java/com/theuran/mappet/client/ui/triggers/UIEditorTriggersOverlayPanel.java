package com.theuran.mappet.client.ui.triggers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.triggers.CommandTrigger;
import com.theuran.mappet.api.triggers.StateTrigger;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.events.UITriggerList;
import com.theuran.mappet.client.ui.triggers.panels.UICommandTriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UIStateTriggerPanel;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.ui.UIKeys;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIEditorOverlayPanel;
import mchorse.bbs_mod.ui.utils.context.ContextMenuManager;
import mchorse.bbs_mod.ui.utils.icons.Icons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIEditorTriggersOverlayPanel extends UIEditorOverlayPanel<Trigger> {
    private final List<Trigger> triggers;

    public UIEditorTriggersOverlayPanel(EventType event) {
        super(UIMappetKeys.TRIGGERS_TITLE);

        this.triggers = Mappet.getEvents().getTriggers(event);

        this.list.sorting().setList(this.triggers);
        this.list.context(menu -> {
            if (this.list.isSelected()) {
                menu.action(Icons.COPY, UIKeys.GENERAL_COPY, () -> {});
                menu.action(Icons.PASTE, UIKeys.GENERAL_PASTE, () -> {});
            }
        });

        this.pickItem(this.triggers.isEmpty() ? null : this.triggers.getFirst(), true);
    }

    @Override
    protected void addNewItem() {
        ContextMenuManager menu = new ContextMenuManager();

        for (Link key : Mappet.getTriggers().getKeys()) {
            IKey label = UIMappetKeys.TRIGGERS_ADD_FORMAT.format(L10n.lang("mappet.triggers.types." + key.path));
            int color = Mappet.getTriggers().getData(key);

            menu.action(Icons.ADD, label, color, () -> {
                Trigger trigger = Mappet.getTriggers().create(key);

                this.triggers.add(trigger);
                this.pickItem(trigger, true);
                this.list.update();
            });
        }

        this.getContext().replaceContextMenu(menu.create());
    }

    @Override
    protected IKey getAddLabel() {
        return UIMappetKeys.TRIGGERS_ADD;
    }

    @Override
    protected IKey getRemoveLabel() {
        return UIMappetKeys.TRIGGERS_REMOVE;
    }

    @Override
    protected UIList<Trigger> createList() {
        return new UITriggerList(list -> this.pickItem(list.getFirst(), false));
    }

    @Override
    protected void fillData(Trigger trigger) {
        this.editor.removeAll();

        try {
            this.editor.add(trigger.getPanel(this));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
