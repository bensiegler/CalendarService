package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public Event getEvent() {
        return new Event();
    }

    @PostMapping("/new/{calID}")
    public Event insertNewEvent(@RequestBody Event event, @PathVariable(value = "calID")Long id) throws CalObjectException, PropertyException {
        return eventService.saveEvent(event, id);
    }
}
