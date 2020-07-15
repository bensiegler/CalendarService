package com.bensiegler.calendarservice.services.events;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.dbmodels.DBEventStore;
import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.repositories.EventPropertyRepo;
import com.bensiegler.calendarservice.repositories.EventRepo;
import com.bensiegler.calendarservice.repositories.PropertyParameterRepo;
import com.bensiegler.calendarservice.repositories.tz.TimeZoneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        DBEventStore DBEventStore = calendarRepo.findByName(name);
//        DBEventStore.setDBEvents(eventRepo.findByCalendarId(DBEventStore.getId()));
//        DBEventStore.setEventProperties(eventPropertyRepo.findByCalendarId(DBEventStore.getId()));
//        DBEventStore.setDBParameters(propertyParameterRepo.findByCalendarId(DBEventStore.getId()));
        Calendar cal = getCalStandardEvents(DBEventStore);

        //TODO have table joining calendars with alarms. Where each calendar specifies
        // the timezoneIDs for the elements that its events need. Then loop through here or in a new class
        // and map them onto a cal object.

        cal.writeCalStreamToFile();
    }

    private Calendar getCalStandardEvents(DBEventStore dbEventStore) throws Exception {
        Calendar calendar = new Calendar();
        calendar.setProductIdentifier(new ProductIdentifier(dbEventStore.getName()));

        for(DBEvent e: dbEventStore.getDBEvents()) {
            DBProperty[] properties = dbEventStore.getEventProperties().stream()
                    .filter(property -> property.getEventId().equals(e.getId())).toArray(DBProperty[]::new);

            Stream<DBParameter> stream;

            Event event = new Event();
            event.setParent(calendar);

            for(DBProperty property: properties) {
                stream = dbEventStore.getDBParameters().stream();
                streamObjectService.mapPropertyOntoCalendarObject(property, stream, event);
            }

            calendar.addCalObject(event);

        }
        return calendar;
    }

}

