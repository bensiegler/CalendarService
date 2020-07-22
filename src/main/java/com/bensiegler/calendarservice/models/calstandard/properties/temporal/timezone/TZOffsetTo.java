package com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.UTCOffset;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class TZOffsetTo extends Property {
    private UTCOffset content;

    public TZOffsetTo() {
        super("TZOFFSETTO");
    }

    public TZOffsetTo(String content) {
        super("TZOFFSETTO");
        setContentUsingString(content);
    }

    public TZOffsetTo(ArrayList<UnknownParameter> extras, UTCOffset content) {
        super("TZOFFSETTO", extras);
        this.content = content;
    }

    public void setPositiveOffset(int hours, int minutes) {
        content = new UTCOffset();
        content.setPositiveOffset(hours, minutes);
    }

    public void setNegativeOffset(int hours, int minutes) {
        content = new UTCOffset();
        content.setNegativeOffset(hours, minutes);
    }

    public UTCOffset getContent() {
        return content;
    }

    public void setContent(UTCOffset content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        int hours;
        int mins;
        if(content.contains("-")) {
            hours = Integer.parseInt(content.substring(1, 3));
            mins = Integer.parseInt(content.substring(3, 5));
            setNegativeOffset(hours, mins);
        }else {
            hours = Integer.parseInt(content.substring(0, 2));
            mins = Integer.parseInt(content.substring(2, 4));
            setPositiveOffset(hours, mins);
        }
    }

    @Override
    public String retrieveContentAsString() {
        return content.toString();
    }
}
