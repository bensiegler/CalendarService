package com.bensiegler.calendarservice.models.properties.temporal.dt.datetime;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.Date;
import com.bensiegler.calendarservice.models.datatypes.DateTime;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.props.End;

public class DateTimeEnd extends End {
    private DateTime content;

    public DateTimeEnd() {
        super(new ValueType("DATE"));
    }

    public DateTimeEnd(Long timeInMillis) {
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
