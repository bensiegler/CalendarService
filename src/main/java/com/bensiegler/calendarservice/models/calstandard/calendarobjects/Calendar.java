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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Calendar {
    //TODO add name field for own system identification
    private ProductIdentifier productIdentifier;
    private final Version version = new Version("2.0");
    private CalendarScale calendarScale = new CalendarScale("GREGORIAN");
    private Method method = new Method("PUBLISH");
    private Color color = new Color("BLUE");

    private ArrayList<TimeZone> timeZones = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();

    //These two are not complete!
    private ArrayList<FreeBusy> freeBusies = new ArrayList<>();
    private ArrayList<ToDo> toDos = new ArrayList<>();

    public void setTimeZones(ArrayList<TimeZone> timeZones) {
        this.timeZones = timeZones;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void setFreeBusies(ArrayList<FreeBusy> freeBusies) {
        this.freeBusies = freeBusies;
    }

    public void setToDos(ArrayList<ToDo> toDos) {
        this.toDos = toDos;
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

    public void addCalObject(CalendarObject calendarObject) {
        if(calendarObject instanceof TimeZone) {
            timeZones.add((TimeZone)calendarObject);
        }else if(calendarObject instanceof Event) {
            events.add((Event) calendarObject);
        }else if(calendarObject instanceof FreeBusy) {
            freeBusies.add((FreeBusy) calendarObject);
        }else if(calendarObject instanceof ToDo) {
            toDos.add((ToDo)calendarObject);
        }
    }

    @Autowired
    TimeZoneService timeZoneService;


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
                String tzid = e.getTZID().getContent().toStringNoName();
                if (!calendarTZIDs.contains(tzid)) {
                    timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                    calendarTZIDs.add(tzid);
                }
            }

            //Event ExceptionDates TZID check
            if(null != e.getExceptionsDates()) {
                for(DateTimeExceptions dte: e.getExceptionsDates()) {
                    String tzid = dte.getTimeZoneIdentifier().toStringNoName();
                    if(!calendarTZIDs.contains(tzid)) {
                        timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                        calendarTZIDs.add(tzid);
                    }
                }
            }

            //Event DateTimeRecurrences TZID check
            if(null != e.getRecurrenceInfo()) {
                for(Recurrence r: e.getRecurrenceInfo()) {
                    String tzid = r.getTimeZoneIdentifier().toStringNoName();
                    if(!calendarTZIDs.contains(tzid)) {
                        timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                        calendarTZIDs.add(tzid);
                    }
                }
            }

            //Event DateTimeStamp TZID check
            if(null != e.getDateTimeStart()) {
                String tzid = e.getDateTimeStart().getTimeZoneIdentifier().toStringNoName();
                if(!calendarTZIDs.contains(tzid)) {
                    timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                    calendarTZIDs.add(tzid);
                }
            }

            //Event DateTimeEnd TZID check
            if(null != e.getEnd()) {
                String tzid = e.getEnd().getTimeZoneIdentifier().toStringNoName();
                if(!calendarTZIDs.contains(tzid)) {
                    timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                    calendarTZIDs.add(tzid);
                }
            }

            //Event RecurrenceInfo TZID check
            if(null != e.getRecurrenceID()) {
                String tzid = e.getRecurrenceID().getTimeZoneIdentifier().toStringNoName();
                if(!calendarTZIDs.contains(tzid)) {
                    timeZones.add(timeZoneService.getTimeZoneByTZID(tzid));
                    calendarTZIDs.add(tzid);
                }
            }


        }
    }

    public void writeCalStreamToFile() throws PropertyException, CalObjectException {
        validate();
        String fileLocation = "/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/" + productIdentifier.getContent();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation))) {
            ArrayList<String> lines = new ArrayList<>();

            lines.add("BEGIN:VCALENDAR");
            lines.add(Property.toCalStream(productIdentifier));
            lines.add(Property.toCalStream(version));
            lines.add(Property.toCalStream(calendarScale));
            lines.add(Property.toCalStream(color));

            for(TimeZone tz: timeZones) {
                lines.addAll(tz.getCalStream());
            }

            for(Event e: events) {
                lines.addAll(e.getCalStream());
            }

            for(ToDo toDo: toDos) {
                lines.addAll(toDo.getCalStream());
            }

            for(FreeBusy freeBusy: freeBusies) {
                lines.addAll(freeBusy.getCalStream());
            }

            lines.add("END:VCALENDAR");
            writeLines(lines, writer);

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void writeLines(ArrayList<String> lines, BufferedWriter writer) throws IOException {
        for(String line: lines) {
            for (int i = 0; i < line.length(); i++) {
                writer.write(line.substring(i, i + 1));
            }
            writer.newLine();
        }
    }
}
