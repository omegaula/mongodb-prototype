package com.roche.mongodb.dynamicforms.template.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.document.mongodb.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    private List<Section> sections = new ArrayList<Section>();

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

    /**
     * Sorted list of sections for the template. Sections should be displayed in the order they are on the list.
     * @return
     */
    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }
}
