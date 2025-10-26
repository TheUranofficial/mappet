package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.scripts.UIScriptEditor;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RunScriptC2SPacket;
import com.theuran.mappet.network.packets.server.SaveScriptC2SPacket;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;

public class UIScriptPanel extends UIDataDashboardPanel<Script> {
    private final UIIcon run;
    private final UITextEditor content;

    public UIScriptPanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.PROPERTIES);

        this.content = new UIScriptEditor(null);

        this.content.relative(this.editor);

        this.run = new UIIcon(Icons.PLAY, this::runScript);
        this.run.tooltip(UIMappetKeys.SCRIPTS_RUN, Direction.LEFT);

        this.editor.add(this.content);

        this.iconBar.add(this.run);

        this.fill(null);
    }

    private void runScript(UIIcon icon) {
        this.save();

        Dispatcher.sendToServer(new RunScriptC2SPacket(this.data.getId(), "main", this.data.getContent()));
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public ContentType getType() {
        return MappetContentType.SCRIPTS;
    }

    @Override
    protected void fillData(Script data) {
        this.updateButtons();

        if (data != null) {
            if (!this.content.getText().equals(data.getContent())) {
                this.content.setText(data.getContent());
            }
        }
    }

    private void updateButtons() {
        this.run.setVisible(this.data != null && this.content.isVisible());
    }

    @Override
    public void forceSave() {
        this.data.setContent(this.content.getText());

        if (this.data != null) {
            Dispatcher.sendToServer(new SaveScriptC2SPacket(this.data.getId(), this.content.getText()));
        }

        super.forceSave();
    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.SCRIPTS_TITLE;
    }
}
