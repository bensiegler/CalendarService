package com.bensiegler.calendarservice.models.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.DTTemplate;

import java.lang.reflect.Field;

public abstract class Due extends DTTemplate {

    public Due(ValueType valueType) {
        super("DUE",valueType);
    }
}
