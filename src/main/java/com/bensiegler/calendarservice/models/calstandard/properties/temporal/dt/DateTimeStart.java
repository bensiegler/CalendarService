package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

public class DateTimeStart extends DTTemplate {

    public DateTimeStart() {
        super("DTSTART", new ValueType("DATETIME"));
    }

    public DateTimeStart(Long timeInMillis) {
        super("DTSTART", new ValueType("DATETIME"));
         setContent(timeInMillis);
    }

    @Override
    public void setContentUsingString(String content) {
        super.content = new DateTime(Long.parseLong(content));
    }
}
