package com.bensiegler.calendarservice.models.calstandard.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Sequence extends Property {
    private Integer content;

    public Sequence() {
        super("SEQUENCE");
    }

    public Sequence(Integer content) {
        super("SEQUENCE");
        this.content = content;
    }

    public Sequence(ArrayList<UnknownParameter> extras, Integer content) {
        super("SEQUENCE", extras);
        this.content = content;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
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
        this.content = Integer.parseInt(content);
    }

    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content);
    }
}
