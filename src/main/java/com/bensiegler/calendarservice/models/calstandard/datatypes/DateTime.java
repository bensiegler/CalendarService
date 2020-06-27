package com.bensiegler.calendarservice.models.calstandard.datatypes;


import java.util.ArrayList;
import java.util.Calendar;

public class DateTime extends Date {

    public DateTime() {
    }

    public DateTime(Long millis) {
        super(millis);
    }



    public String toString() {
        String dateTimeString = super.toString() + "T";
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

        return dateTimeString + "Z";
    }

}
