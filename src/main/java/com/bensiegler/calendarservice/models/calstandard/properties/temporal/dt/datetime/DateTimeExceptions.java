package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.datetime;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.ExceptionsProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class DateTimeExceptions extends ExceptionsProperty {
    private ArrayList<DateTime> content = new ArrayList<>();

    public DateTimeExceptions() {
        super(new ValueType("DATE"));
    }

    public DateTimeExceptions(ArrayList<Long> timesInMillis) {
        super(new ValueType("DATETIME"));
        for(Long l: timesInMillis) {
            this.content.add(new DateTime(l));
        }
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
}
