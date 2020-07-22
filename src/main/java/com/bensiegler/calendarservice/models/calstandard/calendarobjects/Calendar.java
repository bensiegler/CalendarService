package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeExceptions;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence.Recurrence;
import com.bensiegler.calendarservice.services.timezone.TimeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.orm.hibernate4.SpringSessionContext;
import org.springframework.stereotype.Component;

import javax.swing.text.IconView;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Calendar extends CalendarObject {

    @Autowired
    TimeZoneService timeZoneService;

    private ProductIdentifier productIdentifier;
    private Version version = new Version("2.0");

    private CalendarScale calendarScale = new CalendarScale("GREGORIAN");
    private Method method;
    private Color color = new Color("BLUE");

    private ArrayList<TimeZone> timeZones = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();


    public void setTimeZones(ArrayList<TimeZone> timeZones) {
        this.timeZones = timeZones;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }



    public ProductIdentifier getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(ProductIdentifier productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public Version getVersion() {
        return version;
    }

    public CalendarScale getCalendarScale() {
        return calendarScale;
    }

    public void setCalendarScale(CalendarScale calendarScale) {
        this.calendarScale = calendarScale;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void addCalObject(CalendarObject calendarObject) {
        if(calendarObject instanceof TimeZone) {
            timeZones.add((TimeZone)calendarObject);
        }else if(calendarObject instanceof Event) {
            events.add((Event) calendarObject);
        }
    }


    @Override
    public ArrayList<String> retrieveCalStream() throws  PropertyException, CalObjectException {
        validate();
        ArrayList<String> lines = new ArrayList<>();

        lines.add("BEGIN:VCALENDAR");
        lines.add(Property.toCalStream(productIdentifier));
        lines.add(Property.toCalStream(version));
        lines.add(Property.toCalStream(calendarScale));
        lines.add(Property.toCalStream(color));

        for (TimeZone tz : timeZones) {
            lines.addAll(tz.retrieveCalStream());
        }

        for (Event e : events) {
            lines.addAll(e.retrieveCalStream());
        }

        lines.add("END:VCALENDAR");
        return lines;
    }

    public void validate() throws CalObjectException {
        if(null == productIdentifier) {
            throw new CalObjectException("Product Identifier cannot be null");
        }

        if(null == version) {
            throw new CalObjectException("Version cannot be null");
        }

        //////////TIMEZONE VALIDATION//////////

        //Get all TZIDs currently available in Calendar
        ArrayList<String> calendarTZIDs = new ArrayList<>();
        for(TimeZone tz: timeZones) {
            calendarTZIDs.add(tz.getTzid());
        }

        for(Event e: events) {
            //Event TZID check
            if(null != e.getTZID()) {
                try {
                    String tzid = e.getTZID().getContent().toStringNoName();
                    if (!calendarTZIDs.contains(tzid)) {
                        timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                        calendarTZIDs.add(tzid);
                    }
                }catch (NullPointerException ex) {
                    //no timezone specified
                }
            }

            //Event ExceptionDates TZID check
            if(null != e.getExceptionsDates()) {
                try {
                    for(DateTimeExceptions dte: e.getExceptionsDates()) {
                        String tzid = dte.getTimeZoneIdentifier().toStringNoName();
                        if(!calendarTZIDs.contains(tzid)) {
                            timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                            calendarTZIDs.add(tzid);
                        }
                    }
                }catch (NullPointerException ex) {
                    //no timezone specified
                }
            }

            //Event DateTimeRecurrences TZID check
            if(null != e.getRecurrenceInfo()) {
                try {
                    for (Recurrence r : e.getRecurrenceInfo()) {
                        String tzid = r.getTimeZoneIdentifier().toStringNoName();
                        if (!calendarTZIDs.contains(tzid)) {
                            timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                            calendarTZIDs.add(tzid);
                        }
                    }
                }catch (NullPointerException ex) {
                    //no timezone specified
                }
            }

            //Event DateTimeStamp TZID check
            if(null != e.getDateTimeStart()) {
                try {
                    String tzid = e.getDateTimeStart().getTimeZoneIdentifier().toStringNoName();
                    if (!calendarTZIDs.contains(tzid)) {
                        timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                        calendarTZIDs.add(tzid);
                    }
                }catch (NullPointerException ex) {
                    //no timezone specified
                }
            }


            //Event DateTimeEnd TZID check
            if(null != e.getEnd()) {
                try {
                    String tzid = e.getEnd().getTimeZoneIdentifier().toStringNoName();
                    if (!calendarTZIDs.contains(tzid)) {
                        timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                        calendarTZIDs.add(tzid);
                    }
                }catch (NullPointerException ex) {
                    //no timezone specified
                }
            }

            //Event RecurrenceInfo TZID check
            if(null != e.getRecurrenceID()) {
                try {
                    String tzid = e.getRecurrenceID().getTimeZoneIdentifier().toStringNoName();
                    if (!calendarTZIDs.contains(tzid)) {
                        timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                        calendarTZIDs.add(tzid);
                    }
                }catch (NullPointerException ex) {
                    //no timezone specified
                }
            }

            //refresh all timezones from DB
            timeZones = timeZoneService.findTimeZonesByTZIDs(calendarTZIDs);
        }
    }



    public ArrayList<TimeZone> getTimeZones() {
        return timeZones;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
