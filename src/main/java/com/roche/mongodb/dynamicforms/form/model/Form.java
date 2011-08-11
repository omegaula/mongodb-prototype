package com.roche.mongodb.dynamicforms.form.model;

import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

public class Form {

    private ObjectId id;
    private String createdBy;
    private Map<String, Object> fields = new HashMap<String, Object>();

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
