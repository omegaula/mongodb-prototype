package com.roche.mongodb.dynamicforms.entries.converter;

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
        Map<String,String[]> valuesMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        DBObject entry = new BasicDBObject();
        for (Iterator<Map.Entry<String, String[]>> it = valuesMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String[]> param = it.next();
            if (param.getKey().startsWith("entry.")) {
                String key = param.getKey().substring("entry.".length()).replace('.', '-');
                entry.put(key, param.getValue()[0]);
            }
        }
        return entry;
    }

}
