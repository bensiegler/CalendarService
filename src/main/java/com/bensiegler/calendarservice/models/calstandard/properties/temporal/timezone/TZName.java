package com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class TZName extends Property {
    private Language language;
    private String content;

    public TZName() {
        super("TZNAME");
    }

    public TZName(String content) {
        super("TZNAME");
        this.content = content;
    }

    public TZName(ArrayList<UnknownParameter> extras, Language language, String content) {
        super("TZNAME", extras);
        this.language = language;
        this.content = content;
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

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }

    @Override
    public String retrieveContentAsString() {
        return content;
    }
}
