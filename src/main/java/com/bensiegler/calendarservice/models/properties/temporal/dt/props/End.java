package com.bensiegler.calendarservice.models.properties.temporal.dt.props;

import com.bensiegler.calendarservice.models.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.DTTemplate;

import java.lang.reflect.Field;

public abstract class End extends DTTemplate {

    public End(ValueType valueType) {
        super("DTEND", valueType);
    }
}
