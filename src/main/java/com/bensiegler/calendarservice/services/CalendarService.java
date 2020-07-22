package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.dbmodels.DBCalendar;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.services.calstream.CalendarStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

@Service
public class CalendarService {
    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    CalendarStreamService streamService;


    public Calendar getCalendarById(Long id) throws Exception {
        DBCalendar dbCal = calendarRepo.findOne(id);
        Calendar calendar = streamService.getPopulatedCalendar(dbCal);
        return calendar;
    }

    public ArrayList<Calendar> getCalendarsByOwnerId(Long ownerId) throws Exception {
        ArrayList<DBCalendar> dbCalendars = calendarRepo.findByOwnerId(ownerId);
        ArrayList<Calendar> calendars = new ArrayList<>();

        for(DBCalendar es: dbCalendars) {
            calendars.add(streamService.getPopulatedCalendar(es));
        }

        return calendars;
    }

    public Calendar createNewEmptyCalendar(Calendar calendar, Long ownerId) {
        DBCalendar dbCalendar = new DBCalendar();
        dbCalendar.setColor(calendar.getColor().retrieveContentAsString());
        dbCalendar.setCalScale(calendar.getCalendarScale().retrieveContentAsString());
        dbCalendar.setName(calendar.getProductIdentifier().retrieveContentAsString());
        dbCalendar.setOwnerId(ownerId);
        calendarRepo.save(dbCalendar);
        return calendar;
    }

    public ArrayList<String> getCalendarStream(Long id) throws Exception {
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
