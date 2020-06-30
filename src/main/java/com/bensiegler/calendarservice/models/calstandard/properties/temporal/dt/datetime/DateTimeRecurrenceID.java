package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.datetime;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.RecurrenceID;

public class DateTimeRecurrenceID extends RecurrenceID {
    private DateTime content;

    public DateTimeRecurrenceID() {
        super(new ValueType("DATE"));
    }

    public DateTimeRecurrenceID(Long timeInMillis) {
        super(new ValueType("DATETIME"));
        this.content = new DateTime(timeInMillis);
    }

    public Date getContent() {
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
        this.content = new DateTime(Long.parseLong(content));
    }
}
