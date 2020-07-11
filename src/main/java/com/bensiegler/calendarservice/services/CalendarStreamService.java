package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.dbmodels.DBCalendar;
import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.repositories.EventPropertyRepo;
import com.bensiegler.calendarservice.repositories.EventRepo;
import com.bensiegler.calendarservice.repositories.PropertyParameterRepo;
import com.bensiegler.calendarservice.repositories.tz.TimeZoneRepo;
import com.bensiegler.calendarservice.services.events.StreamObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimeZone;
import java.util.stream.Stream;

@Service
public class CalendarStreamService {

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventPropertyRepo eventPropertyRepo;

    @Autowired
    PropertyParameterRepo propertyParameterRepo;

    @Autowired
    StreamObjectService streamObjectService;

    @Autowired
    TimeZoneRepo timeZoneRepo;

    public void generate_iCalendarStream(String name) throws Exception {
        DBCalendar DBCalendar = calendarRepo.findByName(name);
        DBCalendar.setDBEvents(eventRepo.findByCalendarId(DBCalendar.getId()));
        DBCalendar.setEventProperties(eventPropertyRepo.findByCalendarId(DBCalendar.getId()));
        DBCalendar.setDBParameters(propertyParameterRepo.findByCalendarId(DBCalendar.getId()));
        Calendar cal = getCalStandardEvents(DBCalendar);

        //have table joining calendars with alarms. Where each calendar specifies the timezone elements that its events need

        cal.writeCalStreamToFile();
    }

    private Calendar getCalStandardEvents(DBCalendar dbCalendar) throws Exception {
        Calendar calendar = new Calendar();
        calendar.setProductIdentifier(new ProductIdentifier(dbCalendar.getName()));

        for(DBEvent e: dbCalendar.getDBEvents()) {
            DBProperty[] properties = dbCalendar.getEventProperties().stream()
                    .filter(property -> property.getEventId().equals(e.getId())).toArray(DBProperty[]::new);

            Stream<DBParameter> stream;

            Event event = new Event();
            event.setParent(calendar);

            for(DBProperty property: properties) {
                stream = dbCalendar.getDBParameters().stream();
                streamObjectService.mapPropertyOntoCalendarObject(property, stream, event);
            }

            calendar.addCalObject(event);

        }
        return calendar;
    }

}

