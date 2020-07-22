package com.bensiegler.calendarservice.models.calstandard.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class DateTimeStamp extends Property {
    private DateTime content;

    public DateTimeStamp() {
        super("DTSTAMP");
    }

    public DateTimeStamp(Long timeInMillis) {
        super("DTSTAMP");
        this.content = new DateTime(timeInMillis);
    }

    public DateTimeStamp(ArrayList<UnknownParameter> extras, DateTime dateTime) {
        super("DTSTAMP", extras);
        this.content = dateTime;
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

    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content.getContent());
    }
}
