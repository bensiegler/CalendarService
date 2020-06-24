package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;

public class Transparency extends Property {
    private String content;

    public Transparency() {
        super("TRANSP");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) throws ParameterException {
        if(!content.equals("OPAQUE") && !content.equals("TRANSPARENT")) {
            throw new ParameterException("");
        }
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {

    }
}
