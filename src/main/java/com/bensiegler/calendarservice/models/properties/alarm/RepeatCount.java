package com.bensiegler.calendarservice.models.properties.alarm;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;

public class RepeatCount extends Property {
    private Integer content;

    public RepeatCount() {
        super("REPEAT");
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
