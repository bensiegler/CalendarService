package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.parameters.string.Language;
import com.bensiegler.calendarservice.models.properties.Property;

import java.util.ArrayList;

public class Resources extends Property {
    AlternateRepresentation alternateRepresentation;
    Language language;
    ArrayList<?> content;

    public Resources() {
        super("RESOURCES");
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

    public ArrayList<?> getContent() {
        return content;
    }

    public void setContent(ArrayList<?> content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
