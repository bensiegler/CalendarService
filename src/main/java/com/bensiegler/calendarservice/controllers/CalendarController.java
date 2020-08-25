package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.AuthorityException;
import com.bensiegler.calendarservice.exceptions.RequestHeaderException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;


@RestController
@RequestMapping("/cal")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @PreAuthorize("isGrantedViewAuthority(#calendarId)")
    @GetMapping("/{calendarId}")
    public Object getCalendarById(@PathVariable(value = "calendarId") String calendarId,
                                  @RequestHeader(name = "accept-language", defaultValue = "iCalendar")String language) throws Exception {
        if(language.equalsIgnoreCase("iCalendar")) {
            return calendarService.getCalendarById(calendarId).retrieveCalStream();
        }else if(language.equalsIgnoreCase("JSON")) {
            return calendarService.getCalendarById(calendarId);
        }else {
            throw new RequestHeaderException("Please check the \"accept-language\" HTTP header is either iCalendar or JSON");
        }
    }

    @GetMapping("/own")
    public Object getCalendarByOwnerId(@RequestHeader(name = "accept-language", defaultValue = "iCalendar")String language) throws Exception {
        if(language.equalsIgnoreCase("iCalendar")) {
            return calendarService.getCalendarStreamsForAUser();
        }else if(language.equalsIgnoreCase("JSON")) {
            return calendarService.getCalendarsByUser();
        }else{
            throw new RequestHeaderException("Please check the \"accept-language\" HTTP header is either iCalendar or JSON");
        }
    }

    @PreAuthorize("isGrantedEditAuthority(#calendar.getProductIdentifier().retrieveContentAsString())")
    @PatchMapping()
    public Calendar updateCalendar(@RequestBody Calendar calendar) {
        return calendarService.updateCalendar(calendar);
    }

    @PreAuthorize("isGrantedOwnerAuthority(#calendarId)")
    @PatchMapping("transferOwnership/{calendarId}/{newOwnerId}")
    public void transferOwnership(@PathVariable(name = "calendarId")String calendarId,
                                  @PathVariable(name = "newOwnerId")String newOwnerId) {
        calendarService.transferOwnerShip(calendarId, newOwnerId);
    }

    @PreAuthorize("isGrantedDeleteAuthority(#calendarId)")
    @DeleteMapping("/{calendarId}")
    public void deleteCalendar(@PathVariable(name = "calendarId")String calendarId) {
        calendarService.deleteCalendar(calendarId);
    }

    @PostMapping()
    public Calendar createNewEmptyCalendar(@RequestBody Calendar calendar) throws AuthorityException {
        return calendarService.createNewEmptyCalendar(calendar);
    }

    @GetMapping("/iCal/{calendarId}")
    public String get_iCalendarStream(@PathVariable(name = "calendarId")String calendarId) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/testCal"));
        String calStream = calendarService.getCalendarById(calendarId).retrieveCalStream();

        writer.write(calStream);

        writer.close();
        return calStream;
    }

    @GetMapping("/fileread")
    public String fileRead() throws IOException {
        BufferedReader fileReader = new BufferedReader( new FileReader("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/testCal"));

        String s;
        StringBuilder returnString = new StringBuilder();
        ArrayList<String> strings = new ArrayList<>();
        while((s = fileReader.readLine()) != null) {
            strings.add(s);
            returnString.append(s + "\n");
        }

        return returnString.toString();
    }
}
