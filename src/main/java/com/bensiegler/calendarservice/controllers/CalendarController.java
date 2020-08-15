package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.RequestHeaderException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import com.bensiegler.calendarservice.services.CalendarService;
import com.bensiegler.calendarservice.services.calstream.CalendarStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/cal")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("/{calendarID}")
    public Object getCalendarByID(@PathVariable(value = "calendarID") UUID id,
                                  @RequestHeader("accept-language")String language) throws Exception {
        if(language.equalsIgnoreCase("iCalendar")) {
            return calendarService.getCalendarById(id);
        }else if(language.equalsIgnoreCase("JSON")) {
            return calendarService.getCalendarById(id);
        }else {
            throw new RequestHeaderException("Please check the \"accept-language\" HTTP header is either iCalendar or JSON");
        }
    }

    @GetMapping("/own")
    public Object getCalendarByOwnerId(
                                        @RequestHeader("accept-language")String language) throws Exception {
        if(language.equalsIgnoreCase("iCalendar")) {
            return calendarService.getCalendarStreamsForAUser();
        }if(language.equalsIgnoreCase("JSON")) {
            return null;
        }

        return null;
    }

    @PostMapping()
    public Calendar createNewEmptyCalendar(@RequestBody Calendar calendar) {
        return calendarService.createNewEmptyCalendar(calendar);
    }




}
