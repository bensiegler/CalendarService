package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.date;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.Due;

public class DateDue extends Due {
    private Date content;

    public DateDue() {
        super(new ValueType("DATE"));
    }

    public DateDue(Long timeInMillis) {
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

    @Override
    public void setContentUsingString(String content) {
        this.content = new Date(Long.parseLong(content));
    }
}
