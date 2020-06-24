package com.bensiegler.calendarservice.models.properties.temporal.dt;

import com.bensiegler.calendarservice.models.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.TemporalProperty;

import java.lang.reflect.Field;

public abstract class DTTemplate extends TemporalProperty {
    protected ValueType valueType;
    protected TimeZoneIdentifier timeZoneIdentifier;

    public DTTemplate(String name, ValueType valueType) {
        super(name);
        this.valueType = valueType;
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
    protected Field getContentField() throws NoSuchFieldException {
        return this.getClass().getDeclaredField("content");
    }

    @Override
    protected Field[] getNonContentFields() throws NoSuchFieldException {
        return this.getClass().getSuperclass().getSuperclass().getDeclaredFields();
    }
}
