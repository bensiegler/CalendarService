package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class Comment extends Property {
    private AlternateRepresentation alternateRepresentation;
    private Language language;
    private String content;

    public Comment() {
        super("COMMENT");
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
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
