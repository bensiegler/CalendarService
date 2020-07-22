package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.TemporalProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class DateTimeExceptions extends TemporalProperty {
    protected ValueType valueType;
    protected TimeZoneIdentifier timeZoneIdentifier;
    private ArrayList<DateTime> content = new ArrayList<>();

    public DateTimeExceptions() {
        super("EXDATE");
         new ValueType("DATETIME");
    }

    public DateTimeExceptions(String content) {
        super("EXDATE");

        String[] timesInMillis = content.split(",");
        for(String s: timesInMillis) {
            this.content.add(new DateTime(Long.parseLong(s)));
        }
    }

    public DateTimeExceptions(ValueType valueType, TimeZoneIdentifier timeZoneIdentifier,
                              ArrayList<DateTime> content) {
        super("EXDATE");
        this.valueType = valueType;
        this.timeZoneIdentifier = timeZoneIdentifier;
        this.content = content;
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

    public ArrayList<DateTime> getContent() {
        return content;
    }

    public void setContent(ArrayList<Long> timesInMillis) {
        for(Long l: timesInMillis) {
            this.content.add(new DateTime(l));
        }
    }

    @Override
    public void validate() throws PropertyException {
        if(content.size() == 0) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        String[] strings = content.split(",");
        DateTime[] dates = new DateTime[strings.length];
        for(int i = 0; i < strings.length; i++) {
            dates[i] = new DateTime(Long.parseLong(strings[i]));
        }
        this.content = new ArrayList<>(Arrays.asList(dates));
    }

    @Override
    public Field retrieveContentField() throws NoSuchFieldException {
        return this.getClass().getDeclaredField("content");
    }

    @Override
    public Field[] retrieveNonContentFields() {
        return this.getClass().getDeclaredFields();
    }

    @Override
    public String retrieveContentAsString() {
        String contentString = "";

        for(DateTime d: content) {
            contentString += d.getContent() + ",";
        }

        return contentString.substring(0, contentString.length() - 1);
    }
}
