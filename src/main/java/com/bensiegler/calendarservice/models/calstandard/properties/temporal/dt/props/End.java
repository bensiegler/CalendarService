package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

public abstract class End extends DTTemplate {

    public End(ValueType valueType) {
        super("DTEND", valueType);
    }
}