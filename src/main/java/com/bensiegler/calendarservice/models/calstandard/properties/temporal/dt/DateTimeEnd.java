package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

public class DateTimeEnd extends DTTemplate {

    public DateTimeEnd() {
        super("DTEND", new ValueType("DATETIME"));
    }

    public DateTimeEnd(Long timeInMillis) {
        super("DTEND", new ValueType("DATETIME"));
        setContent(timeInMillis);
    }

    @Override
    public void setContentUsingString(String content) {
        super.content = new DateTime(Long.parseLong(content));
    }
}
