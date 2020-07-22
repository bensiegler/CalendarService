package com.bensiegler.calendarservice.models.calstandard.properties.alarm;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Action extends Property {
    private String content;

    public Action() {
        super("ACTION");
    }

    public Action(String content) {
        super("ACTION");
        this.content = content;
    }

    public Action(String name, ArrayList<UnknownParameter> extras, String content) {
        super("ACTION", extras);
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

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }

    @Override
    public String retrieveContentAsString() {
        return content;
    }

}
