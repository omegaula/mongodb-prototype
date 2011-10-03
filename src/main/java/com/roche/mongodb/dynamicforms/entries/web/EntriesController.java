package com.roche.mongodb.dynamicforms.entries.web;

import org.springframework.stereotype.Controller;

import static java.lang.String.format;

@Controller("entriesCtrl")
public class EntriesController {

    public String list(String templateId) {
        return format("/forms/%s/list?faces-redirect=true", templateId);
    }
}
