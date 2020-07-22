package com.bensiegler.calendarservice.models.calstandard.properties.alarm;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class RepeatCount extends Property {
    private Integer content;

    public RepeatCount() {
        super("REPEAT");
    }

    public RepeatCount(Integer content) {
        super("REPEAT");
        this.content = content;
    }

    public RepeatCount(ArrayList<UnknownParameter> extras, Integer content) {
        super("REPEAT", extras);
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
