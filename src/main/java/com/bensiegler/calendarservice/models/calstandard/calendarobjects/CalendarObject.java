package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.UnknownProperty;

import java.io.IOException;
import java.util.ArrayList;

public abstract class CalendarObject {
    private ArrayList<UnknownProperty> unknownProperties = new ArrayList<>();

    public CalendarObject() {
    }

    public CalendarObject(ArrayList<UnknownProperty> unknownProperties) {
        this.unknownProperties = unknownProperties;
    }

    public void addNewUnknownProperty(UnknownProperty unknownProperty) {
        unknownProperties.add(unknownProperty);
    }

    public ArrayList<UnknownProperty> getUnknownProperties() {
        return unknownProperties;
    }

    public abstract ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException;

    public abstract void validate() throws CalObjectException;
}
