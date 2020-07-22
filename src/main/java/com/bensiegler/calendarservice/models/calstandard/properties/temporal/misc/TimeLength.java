package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.time.Duration;
import java.util.ArrayList;

public class TimeLength extends Property {
    private Duration content;

    public TimeLength() {
        super("DURATION");
    }

    public TimeLength(Long content) {
        super("DURATION");
        this.content =  Duration.ofMillis(content);
    }

    public TimeLength(ArrayList<UnknownParameter> extras, Long content) {
        super("DURATION", extras);
        this.content =  Duration.ofMillis(content);
    }

    public Long getContent() {
        return content.toMillis();
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

    @Override
    public void setContentUsingString(String content) {
        this.content = Duration.ofMillis(Integer.parseInt(content));
    }


    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content.toMillis());
    }
}
