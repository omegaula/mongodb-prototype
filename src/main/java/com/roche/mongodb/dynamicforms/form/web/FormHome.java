package com.roche.mongodb.dynamicforms.form.web;

import com.roche.mongodb.dynamicforms.form.model.Form;
import com.roche.mongodb.dynamicforms.form.model.FormRepository;
import com.roche.mongodb.dynamicforms.template.model.FormTemplate;
import com.roche.mongodb.dynamicforms.template.model.FormTemplateRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.document.mongodb.query.Criteria.where;
import static org.springframework.data.document.mongodb.query.Query.query;

@Component("formHome")
public class FormHome {

    private List<Form> forms;
    private Form entry;
    private FormTemplate template;
    private FormRepository formRepository;
    private FormTemplateRepository formTemplateRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public void setFormRepository(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Autowired
    public void setFormTemplateRepository(FormTemplateRepository formTemplateRepository) {
        this.formTemplateRepository = formTemplateRepository;
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public String displayList(String templateId) {
        this.template = mongoTemplate.findOne(query(where("templateId").is(templateId)), FormTemplate.class);
        this.forms = mongoTemplate.find(new Query(), Form.class, template.getTemplateId());
        return "/forms/list";
    }

    public String prepareAdd() {
        this.entry = new Form();
        entry.setId(new ObjectId());
        return "/forms/add";
    }

    public String add() {
        mongoTemplate.save(entry, template.getTemplateId());
        clearState();
        return "/forms/list";
    }

    private void clearState() {
        forms = null;
        entry = null;
    }

    public Form getEntry() {
        return entry;
    }

    public void setEntry(Form entry) {
        this.entry = entry;
    }

    public List<Form> getForms() {
        if (forms == null) {
            this.forms = mongoTemplate.find(new Query(), Form.class, template.getTemplateId());
        }
        return forms;
    }

    public FormTemplate getTemplate() {
        return template;
    }
}
