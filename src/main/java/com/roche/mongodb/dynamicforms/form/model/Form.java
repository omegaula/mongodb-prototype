package com.roche.mongodb.dynamicforms.form.model;

import org.bson.types.ObjectId;

public class Form {

    private ObjectId id;
    private String createdBy;

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
}
