package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Classification extends Property {
    private String content;

    public Classification() {
        super("CLASS");
    }

    public Classification(String content) {
        super("CLASS");
        this.content = content;
    }

    public Classification(ArrayList<UnknownParameter> extras, String content) {
        super("CLASS", extras);
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
            throw new PropertyException("content is not allowed to be null!");
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
