package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.repositories.tz.TimeZoneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class TimeZoneController {

    @Autowired
    TimeZoneRepo timeZoneRepo;

    @GetMapping("/tz")
    public ArrayList<String> dbTimeZone() throws IOException, PropertyException, CalObjectException, IllegalAccessException {
        return timeZoneRepo.getOne(1).getCalStream();
    }
}
