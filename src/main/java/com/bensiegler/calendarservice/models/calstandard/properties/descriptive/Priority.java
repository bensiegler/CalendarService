package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Priority extends Property {
    private Integer content;

    public Priority() {
        super("PRIORITY");
    }

    public Priority(Integer content) {
        super("PRIORITY");
        this.content = content;
    }

    public Priority(ArrayList<UnknownParameter> extras, Integer content) {
        super("PRIORITY", extras);
        this.content = content;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) throws PropertyException {
        if(content <= 0 || content > 9) {
            throw new PropertyException("Priority must be on a range of 0 to 9");
        }
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }

        if(content > 9 || content < 0) {
            throw new PropertyException("Priority must be between 0 and 9");
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
