package com.bensiegler.calendarservice.models.calstandard.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

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

    public void setContent(Long timeInMillis) {
        this.content = new DateTime(timeInMillis);
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        setContent(Long.parseLong(content));
    }
}
