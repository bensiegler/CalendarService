package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class Priority extends Property {
    private Integer content;

    public Priority() {
        super("PRIORITY");
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
    }
}
