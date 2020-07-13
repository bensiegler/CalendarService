package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;

import java.io.IOException;
import java.util.ArrayList;

public class Alarm extends CalendarObject{

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        return null;
    }

    @Override
    public void validate() throws CalObjectException {

    }
}
