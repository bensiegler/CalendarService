package com.bensiegler.calendarservice.models.calstandard.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class Sequence extends Property {
    private Integer content;

    public Sequence() {
        super("SEQUENCE");
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
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
        this.content = Integer.parseInt(content);
    }


}
