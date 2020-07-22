package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.time.Duration;
import java.util.ArrayList;

public class CustomDuration extends Property {
    private java.time.Duration content;

    public CustomDuration() {
        super("DURATION");
    }

    public CustomDuration(Long timeInMillis) {
        super("DURATION");
        content = java.time.Duration.ofMillis(timeInMillis);
        this.content = java.time.Duration.ofSeconds(this.content.toSeconds());
    }

    public CustomDuration(ArrayList<UnknownParameter> extras, java.time.Duration content) {
        super("DURATION", extras);
        this.content = content;
    }


    public java.time.Duration getContent() {
        return content;
    }

    public void setContent(java.time.Duration content) {
        this.content = content;
        this.content = java.time.Duration.ofSeconds(this.content.toSeconds());
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        setContent(java.time.Duration.ofMillis(Long.parseLong(content)));
    }

    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content.toMillis());
    }
}
