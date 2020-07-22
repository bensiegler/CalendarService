package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.services.CalendarService;
import com.bensiegler.calendarservice.services.calstream.CalendarStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cal")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @GetMapping("/{calendarID}")
    public ArrayList<String> getCalStream(@PathVariable(value = "calendarID")Long id) throws Exception {
        return calendarService.getCalendarStream(id);
    }

    @PostMapping()
    public Calendar createNewEmptyCalendar(@RequestBody Calendar calendar) {
        return calendarService.createNewEmptyCalendar(calendar, 1L);
    }
}
