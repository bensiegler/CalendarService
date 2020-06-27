package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;
import java.util.Arrays;

public class Resources extends Property {
    AlternateRepresentation alternateRepresentation;
    Language language;
    ArrayList<String> content;

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

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = new ArrayList<>(Arrays.asList(content));
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
