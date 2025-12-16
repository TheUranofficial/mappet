package com.theuran.mappet.client.ui.scripts.documentation;

import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;

import java.util.ArrayList;
import java.util.List;

public class Docs {
    public List<DocClass> classes = new ArrayList<>();
    public List<DocPackage> packages = new ArrayList<>();

    public static Docs fromData(MapType map) {
        Docs docs = new Docs();

        if (map.has("classes", BaseType.TYPE_LIST)) {
            for (BaseType type : map.getList("classes")) {
                DocClass clazz = new DocClass();

                clazz.fromData(type.asMap());
                docs.classes.add(clazz);
            }
        }

        if (map.has("packages", BaseType.TYPE_LIST)) {
            for (BaseType base : map.getList("packages")) {
                DocPackage pakkage = new DocPackage();

                pakkage.fromData(base.asMap());
                docs.packages.add(pakkage);
            }
        }

        return docs;
    }

    public DocClass getClass(String name) {
        for (DocClass docClass : this.classes) {
            if (docClass.name.endsWith(name)) {
                return docClass;
            }
        }

        return null;
    }

    public DocPackage getPackage(String name) {
        for (DocPackage docPackage : this.packages) {
            if (docPackage.name.equals(name)) {
                return docPackage;
            }
        }

        return null;
    }

    public void remove(String name) {
        this.classes.removeIf(clazz -> clazz.name.endsWith(name));
    }

    public void copyMethods(String from, String... to) {
        DocClass source = this.getClass(from);

        for (String string : to) {
            DocClass target = this.getClass(string);

            target.methods.addAll(source.methods);
        }
    }
}
