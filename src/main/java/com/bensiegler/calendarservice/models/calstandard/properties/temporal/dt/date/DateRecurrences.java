package com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.date;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.Recurrences;

import java.util.ArrayList;
import java.util.Arrays;

public class DateRecurrences extends Recurrences {
    private ArrayList<Date> content = new ArrayList<>();

    public DateRecurrences() {
        super(new ValueType("DATE"));
    }

    public DateRecurrences(ArrayList<Long> timesInMillis) {
        super(new ValueType("DATE"));
        for(Long l: timesInMillis) {
            this.content.add(new Date(l));
        }
    }

    public ArrayList<Date> getContent() {
        return content;
    }

    public void setContent(ArrayList<Long> timesInMillis) {
        for(Long l: timesInMillis) {
            this.content.add(new Date(l));
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
        Date[] dates = new Date[strings.length];
        for(int i = 0; i < strings.length; i++) {
            dates[i] = new Date(Long.parseLong(strings[i]));
        }
        this.content = new ArrayList<>(Arrays.asList(dates));
    }

}
