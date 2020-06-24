package com.bensiegler.calendarservice.models.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.DateTime;
import com.bensiegler.calendarservice.models.properties.Property;

public class Created extends Property {
    private DateTime content;

    public Created() {
        super("CREATED");
    }

    public DateTime getContent() {
        return content;
    }

    public void setContent(DateTime content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
