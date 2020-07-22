package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateTimeRecurrenceID extends DTTemplate {

    public DateTimeRecurrenceID() {
        super("RECURRENCE-ID", new ValueType("DATETIME"));
    }

    public DateTimeRecurrenceID(DateTime dateTime) {
        super("RECURRENCE-ID", new ValueType("DATETIME"));
        this.content = dateTime;
    }

    public DateTimeRecurrenceID(Long timeInMillis) {
        super("RECURRENCE-ID", new ValueType("DATETIME"));
        setContent(timeInMillis);
    }

    public DateTimeRecurrenceID(ArrayList<UnknownParameter> extras, ValueType valueType,
                                TimeZoneIdentifier timeZoneIdentifier, DateTime content) {
        super(extras,"RECURRENCE-ID", valueType, timeZoneIdentifier, content);
    }

    @Override
    public void setContentUsingString(String content) {
        super.content = new DateTime(Long.parseLong(content));
    }
}
