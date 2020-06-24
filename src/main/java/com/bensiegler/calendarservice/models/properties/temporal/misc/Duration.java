package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;

public class Duration extends Property {
    public java.time.Duration content;

    public Duration() {
        super("DURATION");
    }

    public Duration(Long timeInMillis) {
        content = java.time.Duration.ofMillis(timeInMillis);
    }

    public java.time.Duration getContent() {
        return content;
    }

    public void setContent(java.time.Duration content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
