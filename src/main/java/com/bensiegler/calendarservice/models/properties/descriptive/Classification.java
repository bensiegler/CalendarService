package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;

public class Classification extends Property {
    private String content;

    public Classification() {
        super("CLASS");
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
            throw new PropertyException("content is not allowed to be null!");
        }
    }
}
