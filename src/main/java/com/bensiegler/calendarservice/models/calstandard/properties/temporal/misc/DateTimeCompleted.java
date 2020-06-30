package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

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
        if(null == content) {
            throw new PropertyException("content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        this.content = new DateTime(Long.parseLong(content));
    }


}
