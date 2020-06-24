package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.string.Language;
import com.bensiegler.calendarservice.models.properties.Property;

import java.util.ArrayList;

public class Categories extends Property {
    private Language languageParam;
    private ArrayList<?> content = new ArrayList<>();

    public Categories() {
        super("CATEGORIES");
    }

    public Categories(ArrayList<?> content) {
        this.content = content;
    }

    public Language getLanguageParam() {
        return languageParam;
    }

    public void setLanguageParam(Language languageParam) {
        this.languageParam = languageParam;
    }

    public ArrayList<?> getContent() {
        return content;
    }

    public void setContent(ArrayList<?> content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(content.size() == 0) {
            throw new PropertyException("Content field not specified!");
        }
    }
}
