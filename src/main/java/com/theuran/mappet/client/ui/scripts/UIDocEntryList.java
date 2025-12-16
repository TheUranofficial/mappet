package com.theuran.mappet.client.ui.scripts;

import com.theuran.mappet.client.ui.scripts.documentation.DocEntry;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;

import java.util.List;
import java.util.function.Consumer;

public class UIDocEntryList extends UIList<DocEntry> {
    public UIDocEntryList(Consumer<List<DocEntry>> callback) {
        super(callback);

        this.scroll.scrollItemSize = 16;
        this.scroll.scrollSpeed *= 2;
    }

    @Override
    protected boolean sortElements() {
        this.list.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        return true;
    }

    @Override
    protected String elementToString(UIContext context, int i, DocEntry element) {
        return element.getName();
    }
}
