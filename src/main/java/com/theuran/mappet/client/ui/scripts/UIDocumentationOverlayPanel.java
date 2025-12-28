package com.theuran.mappet.client.ui.scripts;

import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.scripts.documentation.*;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.utils.IOUtils;

import java.util.List;

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

        docs = Docs.fromData(DataToString.mapFromString(IOUtils.readText("/assets/mappet/docs.json")));
        entry = null;

        DocList topPackage = new DocList();
        DocList client = new DocList();
        DocList server = new DocList();

        server.name = "Server API";
        server.doc = docs.getPackage("com.theuran.mappet.api.scripts.code").doc;
        server.parent = topPackage;
        client.name = "Client API";
        client.doc = docs.getPackage("com.theuran.mappet.client.api.scripts.code").doc;
        client.parent = topPackage;

        for (DocClass docClass : docs.classes) {
            docClass.setup();

            if (docClass.name.contains("client")) {
                client.entries.add(docClass);
                docClass.parent = client;
            } else {
                server.entries.add(docClass);
                docClass.parent = server;
            }
        }

        topPackage.entries.add(server);
        topPackage.entries.add(client);

        top = topPackage;
    }

    private void setupDocs(DocEntry in) {
        parseDocs();

        if (in != null) {
            entry = in;
        } else if (entry == null) {
            entry = top;
        }

        this.pick(entry);
    }

    private void pick(DocEntry entryIn) {
        boolean isMethod = entryIn instanceof DocMethod;

        entryIn = entryIn.getEntry();

        List<DocEntry> entries = entryIn.getEntries();
        boolean wasSame = this.list.getList().size() >= 2 && this.list.getList().get(1).parent == entryIn.parent;

        if (entry == entryIn || !wasSame) {
            this.list.clear();

            if (entryIn.parent != null) {
                this.list.add(new DocDelegate(entryIn.parent));
            }

            this.list.add(entries);
            this.list.sort();

            if (isMethod) {
                this.list.setCurrentScroll(entryIn);
            }
        }

        this.fill(entryIn);
    }

    private void fill(DocEntry entryIn) {
        if (!(entryIn instanceof DocMethod)) {
            entry = entryIn;
        }

        this.documentation.scroll.scrollTo(0);
        this.documentation.removeAll();
        entryIn.fillIn(this.documentation);

        this.resize();
    }
}
