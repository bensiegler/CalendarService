package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Period;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.PercentComplete;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

//note this property does not extend DateTimeProperty or DateProperty since it can also be of type period and that complicates things
public class RecurrencePeriods extends Recurrence {
    private ArrayList<Period> content;

    public RecurrencePeriods() {
        super("RDATE");
    }

    public RecurrencePeriods(String contentString) {
        super("RDATE");
        String[] dateTimes = contentString.split(",");

        for(String s: dateTimes) {
            Long dateTime = Long.parseLong(s.substring(0, s.indexOf("/")));
            Long duration = Long.parseLong(s.substring(s.indexOf("/")));
            content.add(new Period(dateTime,duration));
        }
    }

    public RecurrencePeriods(ValueType valueType, TimeZoneIdentifier timeZoneIdentifier, ArrayList<Period> periods) {
        super("RDATE", valueType, timeZoneIdentifier);
        this.content = periods;
    }

    public ArrayList<Period> getContent() {
        return content;
    }

    public void setContent(Period[] content) {
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
        String[] stringArr = content.split(",");
        Period[] periods = new Period[stringArr.length];
        String currentString;
        long dateTime;
        long duration;

        for(int i = 0; i < stringArr.length; i++) {
            currentString = stringArr[i];
            dateTime = Long.parseLong(currentString.substring(0, currentString.indexOf("/")));
            duration = Long.parseLong(currentString.substring(currentString.indexOf("/")));
            periods[i] = new Period(dateTime, duration);
        }

        this.content = new ArrayList<>(Arrays.asList(periods));
    }

    @Override
    public String retrieveContentAsString() {
        String contentString = "";

        for(Period p: content) {
            contentString += p.toString() + ",";
        }

        return contentString.substring(0, contentString.length() - 1);
    }
}
