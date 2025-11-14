package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.localization.LocalizationType;

public class MappetLocalization {
    public String getLocalization(String id, String language) {
        return Mappet.getLocalizations().getLocalization(id, LocalizationType.valueOf(language));
    }
}
