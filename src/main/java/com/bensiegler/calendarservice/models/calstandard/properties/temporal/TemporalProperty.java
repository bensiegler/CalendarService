package com.bensiegler.calendarservice.models.calstandard.properties.temporal;

import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class TemporalProperty extends Property {
    public TemporalProperty(String name, ArrayList<UnknownParameter> extras) {
        super(name, extras);
    }

    public TemporalProperty(String name) {
        super(name);
    }

    @Override
    public Field retrieveContentField() throws NoSuchFieldException {
        return this.getClass().getSuperclass().getDeclaredField("content");
    }

    @Override
    public Field[] retrieveNonContentFields() throws NoSuchFieldException {
        return this.getClass().getSuperclass().getDeclaredFields();
    }

}
