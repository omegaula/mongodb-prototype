package com.roche.mongodb.dynamicforms.entries.converter;

import com.google.common.base.Splitter;
import com.mongodb.DBObject;
import org.bson.BSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Decorator class for simplified value querying:
 * instead of
 * <p><code>obj.get('basic').get('name')</code></p>
 * you can use
 * <p><code>obj.get('basic.name')</code></p>.
 *
 * <p>This decorator provides read-only access to decorated object, any methods changing the state of the object will throw RuntimeExceptions.</p>
 */
public class SimplifiedQueryDBObject implements DBObject {

    private DBObject input;

    public SimplifiedQueryDBObject(DBObject input) {
        this.input = input;
    }

    @Override
    public Object get(String key) {
        Iterator<String> fieldNames = Splitter.on('.').split(key).iterator();
        DBObject current = input;
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (fieldNames.hasNext()) {
                current = (DBObject) current.get(fieldName);
                continue;
            }
            return current.get(fieldName);
        }
        return null;
    }

    @Override
    public boolean containsField(String key) {
        Iterator<String> fieldNames = Splitter.on('.').split(key).iterator();
        DBObject current = input;
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (fieldNames.hasNext() && current.containsField(fieldName)) {
                current = (DBObject) current.get(fieldName);
                continue;
            }
            return current.containsField(fieldName);
        }
        return false;
    }

    /* rest of the interface */
    @Override
    public void markAsPartialObject() {
        readOnly();
    }

    private Object readOnly() {
        throw new RuntimeException(SimplifiedQueryDBObject.class + " can be used for read-only operations only!");
    }

    @Override
    public boolean isPartialObject() {
        return input.isPartialObject();
    }

    @Override
    public Object put(String key, Object v) {
        return readOnly();
    }

    @Override
    public void putAll(BSONObject o) {
        readOnly();
    }

    @Override
    public void putAll(Map m) {
        readOnly();
    }

    @Override
    public Map toMap() {
        return input.toMap();
    }

    @Override
    public Object removeField(String key) {
        return readOnly();
    }

    @Override
    public boolean containsKey(String s) {
        return containsField(s);
    }

    @Override
    public Set<String> keySet() {
        return input.keySet();
    }
}
