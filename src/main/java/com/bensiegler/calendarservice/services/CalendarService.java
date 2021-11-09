package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.exceptions.AuthorityException;
import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Method;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.ProductIdentifier;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.UID;
import com.bensiegler.calendarservice.models.dbmodels.*;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import com.bensiegler.calendarservice.services.calstream.CalendarStreamService;
import com.bensiegler.calendarservice.services.calstream.StreamObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

@Service
public class CalendarService {

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    ApplicationContext context;

    @Autowired
    StreamObjectService streamObjectService;

    public Calendar getCalendarById(String id) throws Exception {
        return getPopulatedCalendar(id);
    }

    public HashMap<String, Calendar> getCalendarsByUser() throws Exception {
        HashMap<String, Calendar> calendars = new HashMap<>();

        ArrayList<DBCalendar> ownedCalendars = calendarRepo.findByOwnerId(AuthenticationFacade.getUserId());
        ArrayList<Authority> calendarIdsSharedWithUser = (ArrayList<Authority>) authorityService.getByAuthorityGrantedToAndPowerGiven("VIEW");

        for(Authority authority: calendarIdsSharedWithUser) {
            calendars.put(authority.getCalendarObjectId(), getPopulatedCalendar(authority.getCalendarObjectId()));
        }

        for(DBCalendar calendar: ownedCalendars) {
            calendars.put(calendar.getId(), getPopulatedCalendar(calendar.getId()));
        }

        return calendars;
    }

    public HashMap<String,String> getCalendarStreamsForAUser() throws Exception {
        HashMap<String, String> calendarStreams = new HashMap<>();
        HashMap<String, Calendar> calendars = getCalendarsByUser();

        for(String id: calendars.keySet()) {
            calendarStreams.put(id, calendars.get(id).retrieveCalStream());
        }

        return calendarStreams;
    }

    public Calendar createNewEmptyCalendar(Calendar calendar) throws AuthorityException {
        String userId = AuthenticationFacade.getUserId();
        DBCalendar dbCalendar = new DBCalendar();
        dbCalendar.setColor(calendar.getColor().retrieveContentAsString());
        dbCalendar.setCalScale(calendar.getCalendarScale().retrieveContentAsString());
        dbCalendar.setName(calendar.getName());
        dbCalendar.setOwnerId(userId);

        calendarRepo.save(dbCalendar);

        calendar.setProductIdentifier(new ProductIdentifier(dbCalendar.getId()));

        //grant authorities for user on calendar
        Authority authority = new Authority(null, dbCalendar.getId(), userId, userId, "OWNER");
        authorityService.grantAuthority(authority);
        authority.setPowerGiven("VIEW");
        authorityService.grantAuthority(authority);
        authority.setPowerGiven("EDIT");
        authorityService.grantAuthority(authority);
        authority.setPowerGiven("SHARE");
        authorityService.grantAuthority(authority);
        authority.setPowerGiven("DELETE");
        authorityService.grantAuthority(authority);

        return calendar;
    }

    public Calendar updateCalendar(Calendar calendar) {
        DBCalendar oldDBCalendar = calendarRepo.findOne(calendar.getProductIdentifier().retrieveContentAsString());

        DBCalendar newDBCalendar = new DBCalendar();
        newDBCalendar.setId(calendar.getProductIdentifier().retrieveContentAsString());
        newDBCalendar.setName(calendar.getName());
        newDBCalendar.setCalScale(calendar.getCalendarScale().retrieveContentAsString());
        newDBCalendar.setColor(calendar.getColor().retrieveContentAsString());
        newDBCalendar.setOwnerId(oldDBCalendar.getOwnerId());
        calendarRepo.save(newDBCalendar);

        return calendar;
    }

    public void transferOwnerShip(String calendarId, String newOwner) {
        DBCalendar calendar = calendarRepo.findOne(calendarId);
        calendar.setOwnerId(newOwner);
        calendarRepo.save(calendar);
    }

    public void deleteCalendar(String calendarId) {
        //TODO delete events
        //TODO delete parameters
        //TODO delete properties

        //delete authorities
        authorityService.deleteAllAuthoritiesForAnObject(calendarId);

        //delete calendar
        calendarRepo.delete(calendarId);
    }

    public String writeCalendarStreamToFile(String id) throws Exception {
        Calendar calendar = getCalendarById(id);
        String calStream = calendar.retrieveCalStream();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/test/test.txt"))) {
            writer.write(calStream);
        }
        return calStream;
    }

    public Calendar getPopulatedCalendar(String id) throws Exception {
        DBCalendar dbCalendar = calendarRepo.findOne(id);

        if(null == dbCalendar) {
            throw new CalObjectException("No calendar with id " + id + " could be found");
        }

        Calendar calendar = context.getBean(Calendar.class);
        calendar.setProductIdentifier(new ProductIdentifier(dbCalendar.getOwnerId()));
        calendar.setName(dbCalendar.getName());
        calendar.setMethod(new Method("PUBLISH"));
        for(DBEvent e: dbCalendar.getDBEvents()) {
            DBProperty[] properties = e.getProperties().stream()
                    .filter(property -> property.getCalendarObjectId().equals(e.getId()))
                    .toArray(DBProperty[]::new);

            Stream<DBParameter> stream;

            Event event = new Event();
            event.setUid(new UID(e.getId()));

            for(DBProperty property: properties) {
                stream = property.getParameters().stream();
                streamObjectService.mapPropertyOntoCalendarObject(property, stream, event);
            }

            calendar.addCalObject(event);

        }
        return calendar;
    }
}
