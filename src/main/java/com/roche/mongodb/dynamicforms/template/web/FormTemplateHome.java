package com.roche.mongodb.dynamicforms.template.web;

import com.roche.mongodb.dynamicforms.template.model.Field;
import com.roche.mongodb.dynamicforms.template.model.FormTemplate;
import com.roche.mongodb.dynamicforms.template.model.FormTemplateRepository;
import com.roche.mongodb.dynamicforms.template.model.Section;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component("formTemplateHome")
public class FormTemplateHome {

    private FormTemplateRepository formTemplateRepo;
    private List<FormTemplate> templates;
    private FormTemplate template;
    private Field field;
    private boolean fieldEditingInProgress;

    @Autowired
    public void setFormTemplateRepository(FormTemplateRepository formTemplateRepo) {
        this.formTemplateRepo = formTemplateRepo;
    }

    public String prepareAdd() {
        template = new FormTemplate();
        template.setId(new ObjectId());
        template.addSection(new Section("Content"));
        return "/forms/templates/add";
    }

    public String prepareEdit(ObjectId id) {
        template = formTemplateRepo.findOne(id);
        return "/forms/templates/edit";
    }

    public String save() {
        formTemplateRepo.save(template);
        clearView();
        return "/home";
    }

    public String showDetails(ObjectId id) {
        template = formTemplateRepo.findOne(id);
        return "/forms/templates/details";
    }

    public String delete(ObjectId id) {
        formTemplateRepo.delete(id);
        clearView();
        return "/home";
    }

    public String prepareAddField(ObjectId id) {
         field = new Field();
         markFieldEditingStarted();
         return "ajax";
    }

    public String addField(ObjectId id) {
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

    private void clearView() {
        template = null;
        templates = null;
    }

    public FormTemplate getTemplate() {
        return template;
    }

    public void setTemplate(FormTemplate template) {
        this.template = template;
    }

    public List<FormTemplate> getTemplates() {
        if (templates == null) {
            templates = newArrayList(formTemplateRepo.findAll(sortByTemplateId()));
        }
        return templates;
    }

    private Sort sortByTemplateId() {
        return new Sort("templateId");
    }

}
