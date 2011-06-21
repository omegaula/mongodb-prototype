package com.roche.mongodb.dynamicforms.template.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.document.mongodb.mapping.Document;

/**
 * Template of the form: defines all sections and fields for the form.
 *
 * <p>TemplateId should be constant in the lifetime of the template.</p>
 */
@Document
public class FormTemplate {

    @Id
    private ObjectId id;

    private String templateId;

    private String templateLabel;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateLabel() {
        return templateLabel;
    }

    public void setTemplateLabel(String templateLabel) {
        this.templateLabel = templateLabel;
    }
}
