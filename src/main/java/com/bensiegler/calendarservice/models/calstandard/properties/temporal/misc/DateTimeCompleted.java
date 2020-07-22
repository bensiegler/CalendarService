package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class DateTimeCompleted extends Property {
    private DateTime content;

    public DateTimeCompleted() {
        super("COMPLETED");
    }

    public DateTimeCompleted(Long content) {
        super("COMPLETED");
        this.content = new DateTime(content);
    }

    public DateTimeCompleted(ArrayList<UnknownParameter> extras, DateTime dateTime) {
        super("COMPLETED", extras);
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
            throw new PropertyException("content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        this.content = new DateTime(Long.parseLong(content));
    }

    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content.getContent());
    }

}
