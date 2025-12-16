package com.theuran.mappet.client.ui.scripts;

import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.scripts.documentation.DocEntry;
import com.theuran.mappet.client.ui.scripts.documentation.DocList;
import com.theuran.mappet.client.ui.scripts.documentation.Docs;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.utils.IOUtils;

public class UIDocumentationOverlayPanel extends UIOverlayPanel {
    private static Docs docs;
    private static DocEntry top;
    private static DocEntry entry;

    public UIDocEntryList list;
    public UIScrollView documentation;

    public UIDocumentationOverlayPanel() {
        this(null);
    }

    public UIDocumentationOverlayPanel(DocEntry entry) {
        super(UIMappetKeys.SCRIPTS_DOCUMENTATION_TITLE);

        this.list = new UIDocEntryList((list) -> this.pick(list.getFirst()));
        this.documentation = UI.scrollView(5, 10);

        this.list.relative(this.content).w(120).h(1f);
        this.documentation.relative(this.content).x(120).w(1f, -120).h(1f);

        this.content.add(this.list, this.documentation);

        this.setupDocs(entry);
    }

    private void parseDocs() {
        if (docs != null) {
            return;
        }

        docs = Docs.fromData(DataToString.mapFromString(IOUtils.readText("/assets/docs.json")));
        entry = null;

        DocList topPackage = new DocList();
        DocList scripting = new DocList();
        DocList ui = new DocList();
    }

    private void setupDocs(DocEntry in) {

    }

    private void pick(DocEntry entry) {

    }
}
