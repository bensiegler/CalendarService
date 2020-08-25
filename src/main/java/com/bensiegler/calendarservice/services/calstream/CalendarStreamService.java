package com.bensiegler.calendarservice.services.calstream;

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
import com.bensiegler.calendarservice.repositories.TimeZoneRepo;
import com.bensiegler.calendarservice.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CalendarStreamService {

    @Autowired
    CalendarService calendarService;

    public void generate_iCalendarStream(String id) throws Exception {
        Calendar cal = calendarService.getPopulatedCalendar(id);

        //TODO have table joining calendars with alarms. Where each calendar specifies
        // the timezoneIDs for the elements that its events need. Then loop through here or in a new class
        // and map them onto a cal object.

        cal.retrieveCalStream();
    }



}

