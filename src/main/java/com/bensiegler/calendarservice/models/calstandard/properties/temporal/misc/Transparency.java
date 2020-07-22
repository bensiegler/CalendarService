package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Transparency extends Property {
    private String content;

    public Transparency() {
        super("TRANSP");
    }

    public Transparency(String content) {
        super("TRANSP");
        this.content = content;
    }

    public Transparency(ArrayList<UnknownParameter> extras, String content) {
        super("TRANSP", extras);
        this.content = content;
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
        if(null == content) {
            throw new PropertyException("content cannot be null");
        }

        if(!content.equalsIgnoreCase("OPAQUE") && !content.equalsIgnoreCase("TRANSPARENT")) {
            throw new PropertyException("TRANSP must be either OPAQUE or TRANSPARENT");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }

    @Override
    public String retrieveContentAsString() {
        return content;
    }
}
