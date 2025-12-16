package com.theuran.mappet.client.ui.scripts.documentation;

import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocMethod extends DocEntry {
    public DocReturn returns;
    public List<DocParameter> arguments = new ArrayList<>();
    public List<String> annotations = new ArrayList<>();

    @Override
    public String getName() {
        String args = this.arguments.stream().map(DocParameter::getType).collect(Collectors.joining(", "));

        return super.getName() + "(§2" + args +  "§r)";
    }

    @Override
    public void fillIn(UIScrollView target) {
        super.fillIn(target);

        String reset = "§r";
        String orange = "§7";
        boolean first = true;

        for (DocParameter parameter : this.arguments) {
            UIText text = new UIText(orange + parameter.getType() + reset + " " + parameter.name);

            if (first) {
                text.marginTop(8);
            }

            target.add(text);

            if (!parameter.doc.isEmpty()) {
                DocEntry.process(parameter.doc, target);

                ((UIElement) target.getChildren().getLast()).marginBottom(8);
            }

            first = false;
        }

        target.add(new UIText("Returns " + orange + this.returns.getType()).marginTop(8));

        if (!this.returns.doc.isEmpty()) {
            DocEntry.process(this.returns.doc, target);
        }
    }

    @Override
    public List<DocEntry> getEntries() {
        return this.parent == null ? super.getEntries() : this.parent.getEntries();
    }

    @Override
    public void fromData(MapType data) {
        super.fromData(data);

        this.returns = null;
        this.arguments.clear();
        this.annotations.clear();

        if (data.has("returns")) {
            DocReturn returns = new DocReturn();

            returns.fromData(data.getMap("returns"));
            this.returns = returns;
        }

        if (data.has("arguments", BaseType.TYPE_LIST)) {
            for (BaseType type : data.getList("arguments")) {
                DocParameter parameter = new DocParameter();

                parameter.fromData(type.asMap());
                this.arguments.add(parameter);
            }
        }

        if (data.has("annotations", BaseType.TYPE_LIST)) {
            for (BaseType type : data.getList("annotations")) {
                this.annotations.add(type.asString());
            }
        }
    }
}