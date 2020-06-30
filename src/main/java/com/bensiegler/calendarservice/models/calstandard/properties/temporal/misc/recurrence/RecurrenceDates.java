package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class RecurrenceDates extends Recurrence {
    private ArrayList<Date> content = new ArrayList<>();

    public RecurrenceDates() {
        super("RDATE");
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
    protected Field getContentField() throws NoSuchFieldException {
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


}
