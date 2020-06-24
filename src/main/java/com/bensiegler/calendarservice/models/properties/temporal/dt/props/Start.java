package com.bensiegler.calendarservice.models.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.DTTemplate;

public abstract class Start extends DTTemplate {

    public Start(ValueType valueType) {
        super("DTSTART", valueType);
    }
}
