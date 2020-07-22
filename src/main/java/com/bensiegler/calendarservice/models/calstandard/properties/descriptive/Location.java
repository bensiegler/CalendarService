package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Location extends Property {
    private AlternateRepresentation alternateRepresentation;
    private Language language;
    private String content;

    public Location() {
        super("LOCATION");
    }

    public Location(String content) {
        super("LOCATION");
        this.content = content;
    }

    public Location(ArrayList<UnknownParameter> extras, AlternateRepresentation alternateRepresentation,
                    Language language, String content) {
        super("LOCATION", extras);
        this.alternateRepresentation = alternateRepresentation;
        this.language = language;
        this.content = content;
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

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }

    @Override
    public String retrieveContentAsString() {
        return content;
    }
}

