package com.bensiegler.calendarservice.models.properties.temporal.timezone;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.properties.Property;

public class TZIdentifierProperty extends Property {
    private TimeZoneIdentifier content;

    public TZIdentifierProperty() {
        super("TZID");
    }

    public TimeZoneIdentifier getContent() {
        return content;
    }

    public void setContent(TimeZoneIdentifier content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
