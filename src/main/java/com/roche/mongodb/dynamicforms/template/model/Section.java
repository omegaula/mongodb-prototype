package com.roche.mongodb.dynamicforms.template.model;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private String label;

    private List<Field> fields = new ArrayList<Field>();

    public Section() {
    }

    public Section(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
