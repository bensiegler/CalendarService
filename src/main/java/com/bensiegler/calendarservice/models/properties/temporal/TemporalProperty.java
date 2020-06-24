package com.bensiegler.calendarservice.models.properties.temporal;

import com.bensiegler.calendarservice.models.properties.Property;

import java.lang.reflect.Field;

public abstract class TemporalProperty extends Property {
    public TemporalProperty(String name) {
        super(name);
    }

    @Override
    protected Field getContentField() throws NoSuchFieldException {
        return this.getClass().getSuperclass().getDeclaredField("content");
    }

    @Override
    protected Field[] getNonContentFields() throws NoSuchFieldException {
        return this.getClass().getSuperclass().getDeclaredFields();
    }

}
