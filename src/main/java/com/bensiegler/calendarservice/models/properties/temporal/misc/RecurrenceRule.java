package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;

public class RecurrenceRule extends Property {
    private String content;

    public RecurrenceRule() {
        super("RRULE");
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
}
