package com.bensiegler.calendarservice.models.properties.relational;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

public class UID extends Property {
    private String content;

    public UID() {
        super("UID");
    }

    public UID(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
