package com.bensiegler.calendarservice.models.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TimeZone extends CalendarObject{

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        return new ArrayList<>();
    }

    @Override
    public void validate() throws CalObjectException {

    }
}
