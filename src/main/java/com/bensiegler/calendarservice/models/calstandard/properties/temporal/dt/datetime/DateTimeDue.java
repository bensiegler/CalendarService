package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.datetime;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.Due;

public class DateTimeDue extends Due {
    private DateTime content;

    public DateTimeDue() {
        super(new ValueType("DATE"));
    }

    public DateTimeDue(Long timeInMillis) {
        super(new ValueType("DATE"));
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
}
