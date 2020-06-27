package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.CalendarObjectMappingException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.dbmodel.DBCalendar;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.services.CalendarStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class CalStreamController {

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    CalendarStreamService streamService;

    @GetMapping()
    public String getCalStream() {
        System.out.println("cal requested");
        String calStream = "";
        try (BufferedReader inputReader = new BufferedReader(
                new FileReader("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/64969478593"))) {

            String line;
            while ((line = inputReader.readLine()) != null ) {
                calStream += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calStream;
    }

    @GetMapping("/test/{id}")
    public String getCal(@PathVariable(name = "id") Long id) throws PropertyException, CalendarObjectMappingException, CalObjectException {
         streamService.generate_iCalendarStream(id);
         return "check file";
    }

}
