package com.roche.mongodb.dynamicforms.entries.web;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.RawDBObject;
import com.roche.mongodb.dynamicforms.entries.converter.EntryConverter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.stereotype.Controller;

import java.util.Iterator;
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
        return format("/forms/%s/list?faces-redirect=true", templateId);
    }

    public String prepareEdit(String templateId, ObjectId objectId) {
        entry = mongoTemplate.getCollection(templateId).findOne(query(where("_id").is(objectId)).getQueryObject());
        return format("/forms/%s/edit", templateId);
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
