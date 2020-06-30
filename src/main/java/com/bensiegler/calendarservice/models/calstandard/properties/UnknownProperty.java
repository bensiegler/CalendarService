package com.bensiegler.calendarservice.models.calstandard.properties;

import com.bensiegler.calendarservice.exceptions.PropertyException;

public class UnknownProperty extends Property{
    private String content;

    public UnknownProperty() {
    }

    public UnknownProperty(String name) {
        super(name);
    }

    public UnknownProperty(String name, String content) {
        super(name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content || null == name) {
            throw new PropertyException("Unknown parameters are required to both specify a name and value");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }
}
