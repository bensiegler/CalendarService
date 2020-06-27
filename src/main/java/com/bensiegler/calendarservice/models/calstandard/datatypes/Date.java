package com.bensiegler.calendarservice.models.calstandard.datatypes;

import com.bensiegler.calendarservice.exceptions.PropertyException;

import java.util.Calendar;

public class Date extends DataType {
    Calendar content;

    public Date() {
    }

    public Date(Long millis) {
        this.content = Calendar.getInstance();
        content.setTimeInMillis(millis);
    }

    public Calendar getContent() {
        return content;
    }

    public void setContent(long millis) {
        content = Calendar.getInstance();
        content.setTimeInMillis(millis);
    }

    public String toString() {
        try {
            validate();
        }catch (PropertyException e) {
            throw new IllegalStateException(e.getMessage());
        }

        String year = String.valueOf(content.get(Calendar.YEAR));
        String month = String.valueOf(content.get(Calendar.MONTH) + 1);
        String day = String.valueOf(content.get(Calendar.DAY_OF_MONTH));

        if(month.length() < 2) {
            month = "0" + month;
        }

        if(day.length() < 2) {
            day = "0" + day;
        }

        return year + month + day;
    }

    @Override
    void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Calendar in " + Date.class + " cannot be null");
        }
    }
}
