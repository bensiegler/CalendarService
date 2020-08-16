package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.dbmodels.DBCalendar;
import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import com.bensiegler.calendarservice.services.calstream.CalendarStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class CalendarService {
    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    CalendarStreamService streamService;


    public Calendar getCalendarById(String id) throws Exception {
        return streamService.getPopulatedCalendar(id);
    }

    public HashMap<UUID, Calendar> getCalendarsForAUser() throws Exception {
        //TODO
        HashMap<UUID, Calendar> calendars = new HashMap<>();

        return calendars;
    }

    public HashMap<UUID,ArrayList<String>> getCalendarStreamsForAUser() throws Exception {
        HashMap<UUID, ArrayList<String>> calendarStreams = new HashMap<>();
        HashMap<UUID, Calendar> calendars = getCalendarsForAUser();

        for(UUID id: calendars.keySet()) {
            calendarStreams.put(id, calendars.get(id).retrieveCalStream());
        }

        return calendarStreams;
    }

    public Calendar createNewEmptyCalendar(Calendar calendar) {
        DBCalendar dbCalendar = new DBCalendar();
        dbCalendar.setColor(calendar.getColor().retrieveContentAsString());
        dbCalendar.setCalScale(calendar.getCalendarScale().retrieveContentAsString());
        dbCalendar.setName(calendar.getProductIdentifier().retrieveContentAsString());

        //TODO get owner from authority
        dbCalendar.setOwnerId(AuthenticationFacade.getUserId());
        calendarRepo.save(dbCalendar);
        return calendar;
    }

    public ArrayList<String> writeCalendarStreamToFile(String id) throws Exception {
        Calendar calendar = getCalendarById(id);
        ArrayList<String> calStream = calendar.retrieveCalStream();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/test/test.txt"))) {
            for(String s: calStream) {
                writer.write(s);
                writer.newLine();
            }
        }
        return calStream;
    }
}
