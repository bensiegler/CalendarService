package com.bensiegler.calendarservice.models.properties.temporal.dt.date;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.Date;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.props.Start;

public class DateStart extends Start {
    private Date content;

    public DateStart() {
        super(new ValueType("DATE"));
    }

    public DateStart(Long timeInMillis) {
        super(new ValueType("DATE"));
        this.content = new Date(timeInMillis);
    }

    public Date getContent() {
        return content;
    }

    public void setContent(Long timeInMillis) {
        this.content = new Date(timeInMillis);
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }
}
