package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeExceptions;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence.Recurrence;
import com.bensiegler.calendarservice.services.timezone.TimeZoneService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Data

public class Calendar extends CalendarObject {

    @Autowired
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    TimeZoneService timeZoneService;

    private String name;
    private Version version = new Version("2.0");

    private CalendarScale calendarScale = new CalendarScale("GREGORIAN");
    private Method method;
    private Color color = new Color("BLUE");

    private ArrayList<TimeZone> timeZones = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();


    public void addCalObject(CalendarObject calendarObject) {
        if(calendarObject instanceof TimeZone) {
            timeZones.add((TimeZone)calendarObject);
        }else if(calendarObject instanceof Event) {
            events.add((Event) calendarObject);
        }
    }


    @Override
    public String retrieveCalStream() throws  PropertyException, CalObjectException {
        validate();
        StringBuilder lines = new StringBuilder();

        lines.append("BEGIN:VCALENDAR").append("\n");
        lines.append(Property.toCalStream(super.getProductIdentifier())).append("\n");
        lines.append(Property.toCalStream(version)).append("\n");
        lines.append(Property.toCalStream(calendarScale)).append("\n");
        lines.append(Property.toCalStream(color)).append("\n");

        if(null != method) {
            lines.append(Property.toCalStream(method)).append("\n");
        }

        for (TimeZone tz : timeZones) {
            lines.append(tz.retrieveCalStream());
        }

        for (Event e : events) {
            lines.append(e.retrieveCalStream());
        }

        lines.append("END:VCALENDAR").append("\n");
        return lines.toString();
    }

    public void validate() throws CalObjectException {
        if(null == super.getProductIdentifier()) {
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
            if(null != e.getTZIdentifierProperty()) {
                try {
                    String tzid = e.getTZIdentifierProperty().getContent().toStringNoName();
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
            if(null != e.getDateTimeEnd()) {
                try {
                    String tzid = e.getDateTimeEnd().getTimeZoneIdentifier().toStringNoName();
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
