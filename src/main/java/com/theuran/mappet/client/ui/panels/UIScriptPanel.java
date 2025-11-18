package com.theuran.mappet.client.ui.panels;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.ai.UIAiOverlayPanel;
import com.theuran.mappet.client.ui.ai.UIMascotPanel;
import com.theuran.mappet.client.ui.scripts.UIScriptEditor;
import com.theuran.mappet.client.ui.utils.MappetIcons;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.scripts.ScriptsRunPacket;
import com.theuran.mappet.network.packets.scripts.ScriptsSavePacket;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class UIScriptPanel extends UIDataDashboardPanel<Script> {
    public UIIcon run;
    public UIIcon side;
    public UIIcon ai;
    public UIScriptEditor content;

    public UIAiOverlayPanel aiPanel;

    public UIScriptPanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.PROPERTIES);

        this.content = new UIScriptEditor(null);

        this.content.background().wh(1f, 1f);

        this.content.relative(this.editor);

        this.run = new UIIcon(Icons.PLAY, this::runScript);
        this.run.tooltip(UIMappetKeys.SCRIPTS_RUN, Direction.LEFT);

        this.side = new UIIcon(MappetIcons.SERVER, this::changeSide);
        this.side.tooltip(UIMappetKeys.SCRIPTS_SIDE, Direction.LEFT);

        this.ai = new UIIcon(MappetIcons.API_BBS_FORM, this::aiPanel);
        this.ai.tooltip(UIMappetKeys.AI_OPEN, Direction.LEFT);

        this.editor.add(this.content);

        this.iconBar.add(this.run, this.side, this.ai);

        this.fill(null);
    }

    private void aiPanel(UIIcon icon) {
        this.save();
        UIOverlay.addOverlayRight(this.getContext(), new UIAiOverlayPanel(this.data.getId(), this.data.getContent()), 400);
    }

    private void runScript(UIIcon icon) {
        this.save();

        if (this.data != null) {
            if (this.data.isServer()) {
                Dispatcher.sendToServer(new ScriptsRunPacket(this.data.getId(), "main", this.data.getContent()));
            } else {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                try {
                    Script script = MappetClient.getScripts().getScript(this.data.getId());

                    if (script != null) {
                        script.setContent(this.data.getContent());
                        script.execute(ClientScriptEvent.create(this.data.getId(), "main", player, null, player.clientWorld));
                    }
                } catch (JavetException e) {
                    String message = e.getLocalizedMessage();

                    MinecraftClient.getInstance().player.sendMessage(Text.of(message), false);
                }
            }
        }
    }

    private void changeSide(UIIcon icon) {
        this.data.setServer(!this.data.isServer());

        this.side.both(this.data.isServer() ? MappetIcons.SERVER : MappetIcons.CLIENT);

        if (this.data.isServer()) {
            MappetClient.getScripts().removeScript(this.data.getId());
        }
    }

    @Override
    public ContentType getType() {
        return MappetContentType.SCRIPTS;
    }

    @Override
    public void fill(Script data) {
        this.saveScript();

        super.fill(data);
    }

    @Override
    protected void fillData(Script data) {
        this.updateButtons();

        if (data != null) {
            this.content.setText(data.getContent());
        }
    }

    @Override
    public void fillDefaultData(Script data) {
        data.setContent("function main(c) {\n    \n}");
    }

    private void updateButtons() {
        this.side.setEnabled(this.data != null);
        this.side.both(this.data != null && this.data.isServer() ? MappetIcons.SERVER : MappetIcons.CLIENT);
        this.run.setEnabled(this.data != null);
        this.ai.setEnabled(this.data != null);
    }

    @Override
    public void forceSave() {
        this.data.setContent(this.content.getText());

        this.saveScript();

        super.forceSave();
    }

    private void saveScript() {
        if (this.data != null) {
            Dispatcher.sendToServer(new ScriptsSavePacket(this.data.getId(), this.content.getText(), this.data.isServer()));
        }
    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.SCRIPTS_TITLE;
    }
}
