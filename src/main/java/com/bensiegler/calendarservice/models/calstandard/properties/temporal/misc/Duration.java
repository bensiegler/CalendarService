package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class Duration extends Property {
    public java.time.Duration content;

    public Duration() {
        super("DURATION");
    }

    public Duration(Long timeInMillis) {
        super("DURATION");
        content = java.time.Duration.ofMillis(timeInMillis);
        System.out.println(this.content.toSeconds());
        this.content = java.time.Duration.ofSeconds(this.content.toSeconds());
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
        this.content = java.time.Duration.ofMillis(Long.parseLong(content));
    }


}
