package com.roche.mongodb.dynamicforms.entries.converter;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.stereotype.Service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import java.util.Iterator;
import java.util.Map;

@Service
@ApplicationScoped
public class EntryConverter  {

    public DBObject convertFromRequestParams() {
        DBObject entry = filterParameters();
        return entry;
    }

    private DBObject filterParameters() {
        Map<String,String[]> valuesMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        Map<String, String[]> entryValuesMap = Maps.filterEntries(valuesMap, entryValuesOnly());
        return dbObjectFromValuesMap(entryValuesMap);
    }

    private DBObject dbObjectFromValuesMap(Map<String, String[]> valuesMap) {
        DBObjectBuilder builder = new DBObjectBuilder("entry.");
        for (Map.Entry<String, String[]> entry : valuesMap.entrySet()) {
            builder.setValue(entry.getKey(), entry.getValue());
        }
        return builder.result();
    }

    private Predicate<? super Map.Entry<String, String[]>> entryValuesOnly() {
        return new Predicate<Map.Entry<String, String[]>>() {
            @Override
            public boolean apply(Map.Entry<String, String[]> input) {
                return input.getKey().startsWith("entry.");
            }
        };
    }

}
