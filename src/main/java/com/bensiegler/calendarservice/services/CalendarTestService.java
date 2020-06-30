package com.bensiegler.calendarservice.services;

import com.bensiegler.NumberGenerators;
import com.bensiegler.calendarservice.models.dbmodels.DBCalendar;
import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.repositories.EventPropertyRepo;
import com.bensiegler.calendarservice.repositories.EventRepo;
import com.bensiegler.calendarservice.repositories.PropertyParameterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarTestService {

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventPropertyRepo eventPropertyRepo;

    @Autowired
    PropertyParameterRepo propertyParameterRepo;

    public String insertNewCalendar() {
        DBCalendar newCalendar = new DBCalendar();
        newCalendar.setCalScale("GREGORIAN");
        newCalendar.setColor("BLUE");
        newCalendar.setOwnerId(1L);
        newCalendar.setName(NumberGenerators.randomNumberWithFixedLength(6) + "##testCal");

        newCalendar = calendarRepo.save(newCalendar);
        insertNewEvents(newCalendar.getId());
        return newCalendar.getName();
    }

    public void insertNewEvents(Long calendarId) {
        for(int i = 0; i < 10; i++) {
            String eventName = String.valueOf(NumberGenerators.randomNumberWithFixedLength(6));
            DBEvent newEvent = new DBEvent();
            newEvent.setCalendarId(calendarId);
            newEvent.setName(eventName);
            newEvent = eventRepo.save(newEvent);
            insertNewProperties(calendarId, newEvent.getId());
        }
    }

    public void insertNewProperties(Long calendarId, Long eventId) {
        //set required properties
        DBProperty reqProp = new DBProperty();
        reqProp.setEventId(eventId);
        reqProp.setCalendarId(calendarId);
        reqProp.setName("uid");
        reqProp.setContent(String.valueOf(System.currentTimeMillis()));
        eventPropertyRepo.save(reqProp);

        reqProp.setName("dateTimeStamp");
        reqProp.setContent(String.valueOf(System.currentTimeMillis()));
        eventPropertyRepo.save(reqProp);

        reqProp.setName("dateTimeStart");
        reqProp.setContent(String.valueOf(System.currentTimeMillis()));
        eventPropertyRepo.save(reqProp);

        reqProp.setName("dateTimeEnd");
        reqProp.setContent(String.valueOf(System.currentTimeMillis()));
        eventPropertyRepo.save(reqProp);


        reqProp.setName("dateTimeEnd");
        reqProp.setContent("SUMMARY IS THIS");
        eventPropertyRepo.save(reqProp);


        //create 20 new random properties for the event
        for(int i = 0; i < 5; i++) {
            DBProperty property = new DBProperty();
            property.setName(getRandomPropertyName());
            property.setContent(String.valueOf(System.currentTimeMillis()));
            property.setCalendarId(calendarId);
            property.setEventId(eventId);
            property = eventPropertyRepo.save(property);
//            insertNewParameters(calendarId, eventId, property.getId());
        }
    }

    public String getRandomPropertyName() {
        int rand = NumberGenerators.randomNumberWithFixedLength(2);
        while(rand > 30) {
            rand = NumberGenerators.randomNumberWithFixedLength(2);
        }

        if(rand == 1) {
            return "UID";
        }else if(rand == 2) {
            return "dateTimeStamp";
        }else if(rand == 3) {
            return "classification";
        }else if(rand == 4) {
            return "created";
        }else if(rand == 5) {
            return "description";
        }else if(rand == 6) {
            return "geographicPosition";
        }else if(rand == 7) {
            return "lastModified";
        }else if(rand == 8) {
            return "location";
        }else if(rand == 9) {
            return "organizer";
        }else if(rand == 10) {
            return "priority";
        }else if(rand == 11) {
            return "sequence";
        }else if(rand == 12) {
            return "status";
        }else if(rand == 13) {
            return "summary";
        }else if(rand == 14) {
            return "transparency";
        }else if(rand == 15) {
            return "url";
        }else if(rand == 16) {
            return "recurrenceID";
        }else if(rand == 17) {
            return "recurrenceRule";
        }else if(rand == 18) {
            return "end";
        }else if(rand == 19){
            return "duration";
        }else if(rand == 20) {
            return "attachments";
        }else if(rand == 21) {
            return  "attendees";
        }else if(rand == 22) {
            return "categories";
        }else if(rand == 23) {
            return "comments";
        }else if(rand == 24) {
            return "contacts";
        }else if(rand == 25) {
            return "exceptionsDates";
        }else if(rand == 26) {
            return "relationships";
        }else if(rand == 27) {
            return "resources";
        }else if(rand == 28) {
            return "recurrenceInfo";
        }else {
            return "unknown";
        }
    }

    public void insertNewParameters(Long calendarId, Long eventId, Long PropertyId) {

    }

    public String getRandomParameterName() {
        return "null";
    }
}