package com.roche.mongodb.dynamicforms.template.web;

import com.google.common.collect.Lists;
import com.roche.mongodb.dynamicforms.template.model.FormTemplate;
import com.roche.mongodb.dynamicforms.template.model.FormTemplateRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component("formTemplateHome")
public class FormTemplateHome {

    private FormTemplateRepository formTemplateRepo;
    private List<FormTemplate> templates;
    private FormTemplate template;

    @Autowired
    public void setFormTemplateRepository(FormTemplateRepository formTemplateRepo) {
        this.formTemplateRepo = formTemplateRepo;
    }

    public String prepareAdd() {
        template = new FormTemplate();
        template.setId(new ObjectId());
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

    public String delete(ObjectId id) {
        formTemplateRepo.delete(id);
        clearView();
        return "/home";
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
