package com.roche.mongodb.dynamicforms.entries.web;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.roche.mongodb.dynamicforms.entries.converter.EntryConverter;
import com.roche.mongodb.dynamicforms.entries.converter.SimplifiedQueryDBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.data.document.mongodb.query.Criteria.where;
import static org.springframework.data.document.mongodb.query.Query.query;

@Controller("entriesCtrl")
public class EntriesController {

    private MongoTemplate mongoTemplate;
    private EntryConverter entryConverter;
    private List<DBObject> entries;
    private DBObject entry;

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
        entries = Lists.transform(entries, makeQuerable());
        return format("/forms/%s/list?faces-redirect=true", templateId);
    }

    private Function<? super DBObject, ? extends DBObject> makeQuerable() {
        return new Function<DBObject, DBObject>() {
            @Override
            public DBObject apply(DBObject input) {
                return new SimplifiedQueryDBObject(input);
            }
        };
    }

    public String prepareEdit(String templateId, ObjectId objectId) {
        entry = mongoTemplate.getCollection(templateId).findOne(query(where("_id").is(objectId)).getQueryObject());
        entry = new SimplifiedQueryDBObject(entry);
        return format("/forms/%s/edit", templateId);
    }

    public String prepareAdd(String templateId) {
        entry = null;
        return format("/forms/%s/add?faces-redirect=true", templateId);
    }

    public String save(String templateId) {
        mongoTemplate.getCollection(templateId).save(entryConverter.convertFromRequestParams());
        return list(templateId);
    }

    public String save(String templateId, ObjectId objectId) {
        DBObject dbObject = entryConverter.convertFromRequestParams();
        dbObject.put("_id", objectId);
        mongoTemplate.getCollection(templateId).save(dbObject);
        return list(templateId);
    }

    public List<DBObject> getEntries() {
        return entries;
    }

    public DBObject getEntry() {
        return entry;
    }
}
