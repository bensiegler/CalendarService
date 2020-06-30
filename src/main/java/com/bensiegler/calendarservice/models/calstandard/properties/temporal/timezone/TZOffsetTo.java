package com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.UTCOffset;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class TZOffsetTo extends Property {
    private UTCOffset content;

    public TZOffsetTo() {
        super("TZOFFSETTO");
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
        if(content.contains("+")) {
            hours = Integer.parseInt(content.substring(1, 3));
            mins = Integer.parseInt(content.substring(3, 5));
        }else {
            hours = Integer.parseInt(content.substring(0, 2));
            mins = Integer.parseInt(content.substring(2, 4));
        }
        setNegativeOffset(hours, mins);
    }
}
