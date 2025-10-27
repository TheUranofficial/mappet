package com.theuran.mappet.utils;

import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.forms.forms.Form;

public class FormUtils {
    public static Form fromData(String data) {
        return mchorse.bbs_mod.forms.FormUtils.fromData(DataToString.mapFromString(data));
    }
}
