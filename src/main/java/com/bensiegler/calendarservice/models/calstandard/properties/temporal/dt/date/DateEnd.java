package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.date;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.End;

public class DateEnd extends End {
    private Date content;

    public DateEnd() {
        super(new ValueType("DATE"));
    }

    public DateEnd(Long timeInMillis) {
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
