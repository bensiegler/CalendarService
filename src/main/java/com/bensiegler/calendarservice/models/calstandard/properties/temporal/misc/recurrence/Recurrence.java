package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence;

import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.TemporalProperty;

import java.lang.reflect.Field;

public abstract class Recurrence extends TemporalProperty {
    private ValueType valueType = new ValueType("DATETIME");
    private TimeZoneIdentifier timeZoneIdentifier;

    public Recurrence(String name) {
        super(name);
    }

    public Recurrence(String name, ValueType valueType, TimeZoneIdentifier timeZoneIdentifier) {
        super(name);
        this.valueType = valueType;
        this.timeZoneIdentifier = timeZoneIdentifier;
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
    public Field[] retrieveNonContentFields() throws NoSuchFieldException {
        return this.getClass().getDeclaredFields();
    }


}
