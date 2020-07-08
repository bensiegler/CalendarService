package com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZIdentifierProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZUrl;

import java.io.IOException;
import java.util.ArrayList;

public class TimeZone extends CalendarObject {
    private TZIdentifierProperty TZID;
    private LastModified lastModified;
    private TZUrl tzUrl;

    private Daylight daylight;
    private Standard standard;


    public TZIdentifierProperty getTZID() {
        return TZID;
    }

    public void setTZID(TZIdentifierProperty TZID) {
        this.TZID = TZID;
    }

    public LastModified getLastModified() {
        return lastModified;
    }

    public void setLastModified(LastModified lastModified) {
        this.lastModified = lastModified;
    }

    public TZUrl getTzUrl() {
        return tzUrl;
    }

    public void setTzUrl(TZUrl tzUrl) {
        this.tzUrl = tzUrl;
    }

    public Daylight getDaylight() {
        return daylight;
    }

    public void setDaylight(Daylight daylight) {
        this.daylight = daylight;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:VTIMEZONE");

        lines.add(Property.toCalStream(TZID));
        lines.add(Property.toCalStream(lastModified));
        lines.add(Property.toCalStream(tzUrl));

        lines.addAll(daylight.getCalStream());
        lines.addAll(standard.getCalStream());

        lines.add("END:VTIMEZONE");

        return lines;
    }

    @Override
    public void validate() throws CalObjectException {
        if(null == TZID) {
            throw new CalObjectException("TZID cannot be null");
        }

        if(null == daylight) {
            throw new CalObjectException("Daylight cannot be null");
        }

        if(null == standard) {
            throw new CalObjectException("Standard cannot be null");
        }

    }
}
