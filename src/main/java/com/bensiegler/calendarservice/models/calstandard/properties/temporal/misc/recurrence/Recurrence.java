package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.TemporalProperty;

import java.lang.reflect.Field;

public abstract class Recurrence extends TemporalProperty {
    private ValueType valueType;
    private TimeZoneIdentifier timeZoneIdentifier;

    public Recurrence(String name) {
        super(name);
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public TimeZoneIdentifier getTimeZoneIdentifier() {
        return timeZoneIdentifier;
    }

    public void setTimeZoneIdentifier(TimeZoneIdentifier timeZoneIdentifier) {
        this.timeZoneIdentifier = timeZoneIdentifier;
    }

    @Override
    protected Field[] getNonContentFields() throws NoSuchFieldException {
        return this.getClass().getDeclaredFields();
    }


}
