package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

public abstract class Due extends DTTemplate {

    public Due(ValueType valueType) {
        super("DUE",valueType);
    }
}
