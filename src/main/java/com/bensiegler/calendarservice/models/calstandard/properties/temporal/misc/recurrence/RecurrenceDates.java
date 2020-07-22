package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class RecurrenceDates extends Recurrence {
    private ArrayList<Date> content = new ArrayList<>();

    public RecurrenceDates() {
        super("RDATE");
    }

    public RecurrenceDates(String contentString) {
        super("RDATE");
        String[] dateTimes = contentString.split(",");

        for(String s: dateTimes) {
            content.add(new Date(Long.parseLong(s)));
        }
    }

    public RecurrenceDates(ValueType valueType, TimeZoneIdentifier timeZoneIdentifier, ArrayList<Date> dates) {
        super("RDATE", valueType, timeZoneIdentifier);
        this.content = dates;
    }

    public ArrayList<Date> getContent() {
        return content;
    }

    public void setContent(Date[] content) {
        this.content = new ArrayList<>(Arrays.asList(content));
    }

    @Override
    public void validate() throws PropertyException {
        if(content.size() == 0) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public Field retrieveContentField() throws NoSuchFieldException {
        return this.getClass().getDeclaredField("content");
    }

    @Override
    public void setContentUsingString(String content) {
        String[] strings = content.split(",");
        Date[] dates = new Date[strings.length];
        for(int i = 0; i < strings.length; i++) {
            dates[i] = new Date(Long.parseLong(strings[i]));
        }

        this.content = new ArrayList<>(Arrays.asList(dates));
    }

    @Override
    public String retrieveContentAsString() {
        String contentString = "";

        for(Date d: content) {
            contentString += d.getContent() + ",";
        }

        return contentString.substring(0, contentString.length() - 1);
    }

}
