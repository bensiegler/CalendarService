package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

public abstract class RecurrenceID extends DTTemplate {
    public RecurrenceID(ValueType valueType) {
        super("RECURRENCE-ID", valueType);
    }
}
