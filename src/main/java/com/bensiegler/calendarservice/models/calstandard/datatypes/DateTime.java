package com.bensiegler.calendarservice.models.calstandard.datatypes;


import java.util.ArrayList;
import java.util.Calendar;

public class DateTime extends Date {
    private boolean justDate;

    public DateTime() {
    }

    public DateTime(Long millis) {
        super(millis);
    }

    public void setJustDate(boolean justDate) {
        this.justDate = justDate;
    }

    public String toString() {
        String dateTimeString = super.toString();

        if(!justDate) {
            dateTimeString += "T";
            ArrayList<String> under10s = new ArrayList<>();
            under10s.add(String.valueOf(content.get(Calendar.HOUR_OF_DAY)));
            under10s.add(String.valueOf(content.get(Calendar.MINUTE)));
            under10s.add(String.valueOf(content.get(Calendar.SECOND)));

            for (String s : under10s) {
                if (s.length() < 2) {
                    dateTimeString += "0" + s;
                } else {
                    dateTimeString += s;
                }
            }

            dateTimeString += "Z";
        }

        return dateTimeString;
    }

}
