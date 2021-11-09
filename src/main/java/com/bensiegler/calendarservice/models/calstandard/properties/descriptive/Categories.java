package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;
import java.util.Arrays;

public class Categories extends Property {
    private Language languageParam;
    private ArrayList<String> content = new ArrayList<>();

    public Categories() {
        super("CATEGORIES");
    }

    public Categories(String content) {
        super("CATEGORIES");
        String[] categories = content.split(",");
        this.content = new ArrayList<>(Arrays.asList(categories));
    }

    public Categories(ArrayList<String> content) {
        super("CATEGORIES");
        this.content = content;
    }


    public Categories(ArrayList<UnknownParameter> extras, Language languageParam, ArrayList<String> content) {
        super("CATEGORIES", extras);
        this.languageParam = languageParam;
        this.content = content;
    }

    public Language getLanguageParam() {
        return languageParam;
    }

    public void setLanguageParam(Language languageParam) {
        this.languageParam = languageParam;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = new ArrayList<>(Arrays.asList(content));
    }

    @Override
    public void validate() throws PropertyException {
        if(content.size() == 0) {
            throw new PropertyException("Content field not specified!");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        String[] categories = content.split(",");
        this.content = new ArrayList<>(Arrays.asList(categories));
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
