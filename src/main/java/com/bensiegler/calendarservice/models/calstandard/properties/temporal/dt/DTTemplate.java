package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.TemporalProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.lang.reflect.Field;
import java.util.ArrayList;
public abstract class DTTemplate extends TemporalProperty {
    protected ValueType valueType;
    protected TimeZoneIdentifier timeZoneIdentifier;
    protected DateTime content;

    public DTTemplate(String name, ValueType valueType) {
        super(name);
        this.valueType = valueType;
    }

    public DTTemplate(ArrayList<UnknownParameter> extras, String name, ValueType valueType, TimeZoneIdentifier timeZoneIdentifier, DateTime content) {
        super(name, extras);
        this.valueType = valueType;
        this.timeZoneIdentifier = timeZoneIdentifier;
        this.content = content;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        if(valueType.getValue().equalsIgnoreCase("DATE")) {
            content.setJustDate(false);
        }else{
            content.setJustDate(true);
        }

        this.valueType = valueType;
    }

    public TimeZoneIdentifier getTimeZoneIdentifier() {
        return timeZoneIdentifier;
    }

    public void setTimeZoneIdentifier(TimeZoneIdentifier timeZoneIdentifier) {
        this.timeZoneIdentifier = timeZoneIdentifier;
    }

    public void setContent(Long timeInMillis) {
        this.content = new DateTime(timeInMillis);
    }

    public Date getContent() {
        return content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }



    @Override
    public Field retrieveContentField() throws NoSuchFieldException {
        return this.getClass().getSuperclass().getDeclaredField("content");
    }

    @Override
    public Field[] retrieveNonContentFields() {
        return this.getClass().getSuperclass().getDeclaredFields();
    }

    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content.getContent());
    }
}
