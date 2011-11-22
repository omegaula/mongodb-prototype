package com.roche.mongodb.dynamicforms.entries.converter;

import com.mongodb.DBObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimplifiedQueryDBObjectTest {

    @Test
    public void testSimple() {
        DBObjectBuilder builder = new DBObjectBuilder("a.");
        builder.setValue("a.name", new String[] {"Ann"});
        builder.setValue("a.partner.name", new String[] {"Mark"});
        DBObject obj = new SimplifiedQueryDBObject(builder.result());

        assertEquals("Ann", obj.get("name"));
        assertTrue(obj.containsField("name"));
        assertEquals("Mark", obj.get("partner.name"));
        assertTrue(obj.containsField("partner.name"));
        assertFalse(obj.containsField("mother.name"));
        assertNull(obj.get("abc.xyz"));
    }
}
