package com.theuran.mappet.client.ui.scripts.documentation;

public class DocDelegate extends DocEntry {
    public DocEntry delegate;

    public DocDelegate(DocEntry delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getName() {
        return "../";
    }

    @Override
    public DocEntry getEntry() {
        return this.delegate;
    }
}
