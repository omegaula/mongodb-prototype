package com.roche.mongodb.dynamicforms.entries.web;

import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import com.mongodb.util.Util;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Controller;

@Controller("entriesJsonCtrl")
public class EntriesJsonController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public String saveFromJson() {
        DBObject parsedObject = (DBObject) JSON.parse("{'name':'new Ula', 'surname': 'Krukar', '_id':'4e9f044d308d77f541e95991'}");
        if (parsedObject.containsField("_id")) {
            Object value = parsedObject.get("_id");
            parsedObject.put("_id", new ObjectId(value.toString()));
        }
        mongoTemplate.getCollection("test").save(parsedObject);

        return "home.xhtml";
    }

}
