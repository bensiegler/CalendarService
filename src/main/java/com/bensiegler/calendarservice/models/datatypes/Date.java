package com.bensiegler.calendarservice.models.datatypes;

import com.bensiegler.calendarservice.exceptions.PropertyException;

import java.util.Calendar;

public class Date extends DataType {
    Calendar cal;

    public Date() {
    }

    public Date(Long millis) {
        this.cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(long millis) {
        cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
    }

    public String toString() {
        try {
            validate();
        }catch (PropertyException e) {
            throw new IllegalStateException(e.getMessage());
        }
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH));
        String day = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));

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
        if(null == cal) {
            throw new PropertyException("Calendar in " + Date.class + " cannot be null");
        }
    }
}
