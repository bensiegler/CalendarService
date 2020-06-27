package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;
import java.util.Arrays;

public class Categories extends Property {
    private Language languageParam;
    private ArrayList<String> content = new ArrayList<>();

    public Categories() {
        super("CATEGORIES");
    }

    public Categories(ArrayList<String> content) {
        super("CATEGORIES");
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
}
