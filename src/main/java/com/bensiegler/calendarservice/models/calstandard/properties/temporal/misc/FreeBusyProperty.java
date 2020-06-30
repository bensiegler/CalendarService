package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Period;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.FreeBusyType;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;
import java.util.Arrays;

public class FreeBusyProperty extends Property {
    private FreeBusyType freeBusyType;
    private ArrayList<Period> content = new ArrayList<>();

    public FreeBusyProperty() {
        super("FREEBUSY");
    }

    public FreeBusyType getFreeBusyType() {
        return freeBusyType;
    }

    public void setFreeBusyType(FreeBusyType freeBusyType) {
        this.freeBusyType = freeBusyType;
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
            throw new PropertyException("You must include at least one Period");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        String[] stringArr = content.split(",");
        Period[] periods = new Period[stringArr.length];
        String currentString;
        Long dateTime;
        Long duration;
        for(int i = 0; i < stringArr.length; i++) {
            currentString = stringArr[i];
            dateTime = Long.parseLong(currentString.substring(0, currentString.indexOf("/")));
            duration = Long.parseLong(currentString.substring(currentString.indexOf("/")));
            periods[i] = new Period(dateTime, duration);
        }

        this.content = new ArrayList<>(Arrays.asList(periods));
    }
}
