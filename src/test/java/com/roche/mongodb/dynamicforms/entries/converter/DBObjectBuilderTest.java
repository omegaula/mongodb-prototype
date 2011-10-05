package com.roche.mongodb.dynamicforms.entries.converter;

import com.mongodb.DBObject;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBObjectBuilderTest {

    @Test
    public void testSimple() {
        DBObjectBuilder builder = new DBObjectBuilder("entry.");
        builder.setValue("entry.basic.name", new String[]{"Ann"});
        builder.setValue("entry.basic.surname", new String[]{"Smith"});
        builder.setValue("entry.position.name", new String[]{"Manager"});
        builder.setValue("entry.creationDate", new String[]{"today"});
        DBObject actualResult = builder.result();
        assertEquals("today", actualResult.get("creationDate"));
        DBObject basic = (DBObject) actualResult.get("basic");
        assertEquals("Ann", basic.get("name"));
        assertEquals("Smith", basic.get("surname"));
        DBObject position = (DBObject) actualResult.get("position");
        assertEquals("Manager", position.get("name"));
    }

    @Test(expected = RuntimeException.class)
    public void testExceptionSimple() {
        DBObjectBuilder builder = new DBObjectBuilder("entry.");
        builder.setValue("entry.basic.name", new String[]{"Ann"});
        builder.setValue("entry.basic", new String[]{"Smith"});
    }

    @Test(expected = RuntimeException.class)
    public void testException() {
        DBObjectBuilder builder = new DBObjectBuilder("entry.");
        builder.setValue("entry.xyz.basic.name", new String[]{"Ann"});
        builder.setValue("entry.xyz.basic", new String[]{"Smith"});
    }

}
