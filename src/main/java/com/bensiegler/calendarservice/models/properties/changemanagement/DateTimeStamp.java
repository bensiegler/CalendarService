package com.bensiegler.calendarservice.models.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.DateTime;
import com.bensiegler.calendarservice.models.properties.Property;
import com.bensiegler.calendarservice.models.properties.temporal.dt.DTTemplate;

public class DateTimeStamp extends Property {
    private DateTime content;

    public DateTimeStamp() {
        super("DTSTAMP");
    }

    public DateTimeStamp(Long timeInMillis) {
        super("DTSTAMP");
        this.content = new DateTime(timeInMillis);
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
