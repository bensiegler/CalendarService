package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

import java.util.ArrayList;

public class DateTimeDue extends DTTemplate {

    public DateTimeDue() {
        super("DUE", new ValueType("DATETIME"));
    }

    public DateTimeDue(Long timeInMillis) {
        super("DUE",new ValueType("DATETIME"));
        setContent(timeInMillis);
    }

    public DateTimeDue(DateTime dateTime) {
        super("DUE", new ValueType("DATETIME"));
        this.content = dateTime;
    }

    public DateTimeDue(ArrayList<UnknownParameter> extras, ValueType valueType, TimeZoneIdentifier timeZoneIdentifier, DateTime content) {
        super(extras, "DUE", valueType, timeZoneIdentifier, content);
    }

    @Override
    public void setContentUsingString(String content) {
        super.content = new DateTime(Long.parseLong(content));
    }
}
