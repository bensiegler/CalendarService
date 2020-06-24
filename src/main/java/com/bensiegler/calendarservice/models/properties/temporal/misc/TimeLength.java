package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;

import java.time.Duration;

public class TimeLength extends Property {
    private Duration content;

    public TimeLength() {
        super("DURATION");
    }

    public java.time.Duration getContent() {
        return content;
    }

    public void setContent(java.time.Duration duration) {
        this.content = duration;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
