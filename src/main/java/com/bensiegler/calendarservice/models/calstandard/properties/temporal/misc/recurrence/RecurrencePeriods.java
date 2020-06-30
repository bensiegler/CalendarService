package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Period;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

//note this property does not extend DateTimeProperty or DateProperty since it can also be of type period and that complicates things
public class RecurrencePeriods extends Recurrence {
    private ArrayList<Period> content;

    public RecurrencePeriods() {
        super("RDATE");
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
    protected Field getContentField() throws NoSuchFieldException {
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
}
