package com.fahmtechnologies.speechtotext.Model;

import java.io.Serializable;

public class Languages {

    private String strLaguages;
    private String strLangId;

    public Languages(String strLangId, String strLaguages) {
        this.strLaguages = strLaguages;
        this.strLangId = strLangId;
    }

    public String getStrLaguages() {
        return strLaguages;
    }

    public String getStrLangId() {
        return strLangId;
    }
}
