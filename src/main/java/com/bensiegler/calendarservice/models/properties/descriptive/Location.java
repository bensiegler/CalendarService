package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.parameters.string.Language;
import com.bensiegler.calendarservice.models.properties.Property;

public class Location extends Property {
    private AlternateRepresentation alternateRepresentation;
    private Language language;
    private String content;

    public Location() {
        super("LOCATION");
    }

    public AlternateRepresentation getAlternateRepresentation() {
        return alternateRepresentation;
    }

    public void setAlternateRepresentation(AlternateRepresentation alternateRepresentation) {
        this.alternateRepresentation = alternateRepresentation;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(content == null) {
            throw new PropertyException("Content cannot be null");
        }
    }
}