package com.bensiegler.calendarservice.models.datatypes;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.Parameter;

public class UTCOffset extends DataType {
    private String content;

    public UTCOffset() {
    }

    public void setNegativeOffset(int hours, int minutes) {
        content = "-" + format(hours) + format(minutes);
    }

    public void setPositiveOffset(int hours, int minutes) {
        content = "+" + format(hours) + format(minutes);
    }

    public String getOffset() {
        return content;
    }

    private String format(int num) {
        String s = String.valueOf(num);

        if(s.length() < 2) {
            return  "0" + s;
        }else {
            return s;
        }
    }

    @Override
    void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public String toString() {
        return content;
    }
}
