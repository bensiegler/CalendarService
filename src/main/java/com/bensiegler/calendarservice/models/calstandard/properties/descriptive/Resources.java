package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
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

    public Resources(String content) {
        super("RESOURCES");
        String[] resources = content.split(",");
        this.content = new ArrayList<>(Arrays.asList(resources));
    }

    public Resources(ArrayList<UnknownParameter> extras, AlternateRepresentation alternateRepresentation,
                     Language language, ArrayList<String> content) {
        super("RESOURCES", extras);
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

    @Override
    public void setContentUsingString(String content) {
        String[] resources = content.split(",");
        this.content = new ArrayList<>(Arrays.asList(resources));
    }

    @Override
    public String retrieveContentAsString() {
        String contentString = "";

        for(String s: content) {
            contentString += s + ",";
        }

        return contentString.substring(0, contentString.length() - 1);
    }
}
