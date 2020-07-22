package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class PercentComplete extends Property {
    private Integer content;

    public PercentComplete() {
        super("PERCENT-COMPLETE");
    }

    public PercentComplete(Integer content) {
        super("PERCENT-COMPLETE");
        this.content = content;
    }

    public PercentComplete(ArrayList<UnknownParameter> extras, Integer content) {
        super("PERCENT-COMPLETE", extras);
        this.content = content;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        if(content > 100 || content < 0) {
            throw new ArithmeticException("A percentage cannot be greater than 100 or less than 0");
        }
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
