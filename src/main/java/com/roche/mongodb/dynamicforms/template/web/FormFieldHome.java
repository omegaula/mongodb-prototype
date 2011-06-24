package com.roche.mongodb.dynamicforms.template.web;

import com.roche.mongodb.dynamicforms.template.model.Field;
import com.roche.mongodb.dynamicforms.template.model.FormTemplate;
import com.roche.mongodb.dynamicforms.template.model.FormTemplateRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("formFieldHome")
public class FormFieldHome {

    private FormTemplate template;
    private Field field;
    private boolean fieldEditingInProgress;
    private FormTemplateRepository formTemplateRepository;

    @Autowired
    public void setFormTemplateRepository(FormTemplateRepository formTemplateRepository) {
        this.formTemplateRepository = formTemplateRepository;
    }

    public String showDetails(ObjectId id) {
        template = formTemplateRepository.findOne(id);
        return "/forms/templates/details";
    }

    public String prepareAddField() {
        field = new Field();
        markFieldEditingStarted();
        return "ajax";
    }

    public String addField() {
        template.getSections().get(0).getFields().add(this.field);
        formTemplateRepository.save(template);
        markFieldEditingFinished();
        return "ajax";
    }

    private void markFieldEditingFinished() {
        this.fieldEditingInProgress = false;
    }

    private void markFieldEditingStarted() {
        this.fieldEditingInProgress = true;
    }

    public boolean isFieldEditingInProgress() {
        return fieldEditingInProgress;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public FormTemplate getTemplate() {
        return template;
    }
}
