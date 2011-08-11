package com.roche.mongodb.dynamicforms.form.model.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roche.mongodb.dynamicforms.form.model.Form;
import org.bson.types.ObjectId;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FormConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();
        pairs.add(new ConvertiblePair(DBObject.class, Form.class));
        pairs.add(new ConvertiblePair(Form.class, DBObject.class));
        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (DBObject.class.isAssignableFrom(sourceType.getType()) && targetType.getType() == Form.class) {
            return readForm((DBObject)source);
        }
        if (sourceType.getType() == Form.class && targetType.getType() == DBObject.class) {
            return writeForm((Form) source);
        }
        return null;
    }

    DBObject writeForm(Form source) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.append("_id", source.getId());
        dbObject.append("createdBy", source.getCreatedBy());
        BasicDBObject fields = new BasicDBObject();
        for (Map.Entry<String, Object> field : source.getFields().entrySet()) {
            fields.append(field.getKey(), field.getValue());
        }
        dbObject.append("fields", fields);
        return dbObject;
    }

    Form readForm(DBObject source) {
        Form form = new Form();
        form.setId(new ObjectId(source.get("_id").toString()));
        form.setCreatedBy(source.get("createdBy").toString());
        DBObject fields = (DBObject) source.get("fields");
        for (String key : fields.keySet()) {
            form.getFields().put(key, fields.get(key));
        }
        return form;
    }
}
