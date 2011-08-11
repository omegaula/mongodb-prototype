package com.roche.mongodb.dynamicforms.form.model.converter;

import com.mongodb.DBObject;
import com.roche.mongodb.dynamicforms.form.model.Form;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormConverterTest {

    private FormConverter converter = new FormConverter();

    @Test
    public void testConvert() throws Exception {
        Form form = new Form();
        form.setId(new ObjectId());
        form.setCreatedBy("ooooo");
        form.getFields().put("aaa", "nnn");
        form.getFields().put("bbb", "www");

        DBObject dbObject = converter.writeForm(form);
        Form converted = converter.readForm(dbObject);

        assertEquals(form.getCreatedBy(), converted.getCreatedBy());
        assertEquals(form.getFields(), converted.getFields());
    }
}
