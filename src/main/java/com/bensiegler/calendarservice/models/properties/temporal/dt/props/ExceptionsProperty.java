package com.bensiegler.calendarservice.models.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.DTTemplate;

public abstract class ExceptionsProperty extends DTTemplate {

    public ExceptionsProperty(ValueType valueType) {
        super("EXDATE", valueType);
    }
}