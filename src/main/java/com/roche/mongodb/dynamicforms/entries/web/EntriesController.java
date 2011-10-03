package com.roche.mongodb.dynamicforms.entries.web;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.roche.mongodb.dynamicforms.entries.converter.EntryConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Controller;

import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

@Controller("entriesCtrl")
public class EntriesController {

    private MongoTemplate mongoTemplate;
    private EntryConverter entryConverter;
    private List<DBObject> entries;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public void setEntryConverter(EntryConverter entryConverter) {
        this.entryConverter = entryConverter;
    }

    public String list(String templateId) {
        entries = mongoTemplate.getCollection(templateId).find().toArray();
        return format("/forms/%s/list?faces-redirect=true", templateId);
    }

    public String save(String templateId) {
        mongoTemplate.getCollection(templateId).save(entryConverter.convertFromRequestParams());
        return list(templateId);
    }

    public List<DBObject> getEntries() {
        return entries;
    }
}
