package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import com.bensiegler.calendarservice.services.timezone.TimeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/tz")
public class TimeZoneController {

    @Autowired
    TimeZoneService timeZoneService;

    @GetMapping("/{continent}/{city}")
    public TimeZone dbTimeZone(@PathVariable(value = "continent")String continent, @PathVariable(value = "city")String city) throws IOException, PropertyException, CalObjectException, IllegalAccessException {
        return timeZoneService.getTimeZoneByTZID(continent + " / " + city);
    }

    @GetMapping("/all")
    public ArrayList<TimeZone> getAllTimeZones() {
        return timeZoneService.getAllTimeZones();
    }

    @PostMapping
    public TimeZone insertNewTimezone(@RequestBody TimeZone timeZone) throws CalObjectException {
        return timeZoneService.createNewTimeZone(timeZone);
    }




}
