package com.bensiegler.calendarservice.models.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;

import java.io.BufferedWriter;
import java.io.IOException;

public class FreeBusy extends CalendarObject{

    @Override
    public void writeToCalStreamFile(BufferedWriter writer) throws IllegalAccessException, PropertyException, CalObjectException, IOException {

    }

    @Override
    public void validate() throws CalObjectException {

    }
}
