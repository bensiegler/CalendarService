package com.bensiegler.calendarservice.models.properties.temporal.dt.date;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.Date;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.dt.props.Recurrences;

import java.util.ArrayList;

public class DateRecurrences extends Recurrences {
    private final ArrayList<Date> content = new ArrayList<>();

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

}
