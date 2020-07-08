package com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class TZIdentifierProperty extends Property {
    private TimeZoneIdentifier content;

    public TZIdentifierProperty() {
        super("TZID");
    }

    public TZIdentifierProperty(String country, String city) {
        super("TZID");
        this.content = new TimeZoneIdentifier(country, city);
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

    @Override
    public void setContentUsingString(String content) {
        String[] holder = content.split("/");
        this.content = new TimeZoneIdentifier(holder[0], holder[1]);
    }


}
