package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.DateTime;
import com.bensiegler.calendarservice.models.properties.Property;

//note this class only extends property and not DateTimeProperty as is does not allow TZID and VALUE fields.
public class DateTimeCompleted extends Property {
    private DateTime content;

    public DateTimeCompleted() {
        super("COMPLETED");
    }

    public DateTime getContent() {
        return content;
    }

    public void setContent(Long timeInMillis) {
        this.content = new DateTime(timeInMillis);
    }

    @Override
    public void validate() throws PropertyException {

    }
}
