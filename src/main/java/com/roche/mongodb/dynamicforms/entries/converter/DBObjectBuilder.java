package com.roche.mongodb.dynamicforms.entries.converter;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Iterator;

public class DBObjectBuilder {

    /**
     * Prefix to be ignored in value name
     */
    private final String prefix;
    private final BasicDBObject result;

    public DBObjectBuilder(String prefix) {
        this.prefix = prefix;
        result = new BasicDBObject();
    }

    public void setValue(String keyWithPrefix, String[] value) {
        String key = removePrefix(keyWithPrefix);
        Iterator<String> iterator = Splitter.on('.').split(key).iterator();
        BasicDBObject currentObject = result;
        while (iterator.hasNext()) {
            String hashName = iterator.next();
            if (iterator.hasNext()) {
                currentObject = getObject(currentObject, hashName);
                continue;
            }
            putIfPossible(currentObject, hashName, value[0]);
        }
    }

    private void putIfPossible(BasicDBObject currentObject, String hashName, String val) {
        if (currentObject.containsField(hashName)) {
            throw new RuntimeException(hashName + " is a primitive value, where it should be a hash!");
        }
        currentObject.put(hashName, val);
    }

    private BasicDBObject getObject(BasicDBObject currentObject, String hashName) {
        if (currentObject.containsField(hashName)) {
            Object field = currentObject.get(hashName);
            assertIsNotPrimitive(hashName, field);
            return (BasicDBObject) field;
        }
        else {
            BasicDBObject dbObject = new BasicDBObject();
            currentObject.put(hashName, dbObject);
            return dbObject;
        }
    }

    private void assertIsNotPrimitive(String hashName, Object field) {
        if (!(field instanceof BasicDBObject)) {
            throw new RuntimeException(hashName + " is a primitive value, where it should be a hash!");
        }
    }

    private String removePrefix(String keyWithPrefix) {
        return keyWithPrefix.startsWith(prefix) ? keyWithPrefix.substring(prefix.length()) : keyWithPrefix;
    }

    public DBObject result() {
        return result;
    }
}
