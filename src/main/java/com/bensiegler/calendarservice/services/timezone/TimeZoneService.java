package com.bensiegler.calendarservice.services.timezone;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import com.bensiegler.calendarservice.repositories.TimeZoneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeZoneService {

    @Autowired
    TimeZoneRepo timeZoneRepo;

    ////////////////////////////READ//////////////////////////////
    public TimeZone getTimeZoneByTZID(String TZID) {
        return timeZoneRepo.findOne(TZID);
    }

    public ArrayList<TimeZone> findTimeZonesByTZIDs(ArrayList<String> tzids) {
        List<TimeZone> list = timeZoneRepo.findAll(tzids);
        if(list.size() == 0) {
            return new ArrayList<>();
        }
        return (ArrayList<TimeZone>)list;
    }

    public ArrayList<TimeZone> getAllTimeZones() {
        List<TimeZone> list = timeZoneRepo.findAll();
        if(list.size() == 0) {
            return new ArrayList<>();
        }
        return(ArrayList<TimeZone>) list;
    }

    ////////////////////////////CREATE////////////////////////////
    public TimeZone createNewTimeZone(TimeZone timeZone) throws CalObjectException {
        timeZone.validate();
        return timeZoneRepo.save(timeZone);
    }

    public ArrayList<TimeZone> batchCreateNewTimeZones(ArrayList<TimeZone> timeZones) throws CalObjectException {
        for(TimeZone tz: timeZones) {
            tz.validate();
        }

        return (ArrayList<TimeZone>)timeZoneRepo.save(timeZones);
    }

    ////////////////////////////DELETE////////////////////////////
    public void deleteTimeZoneByTZID(String tzid) {
        timeZoneRepo.delete(tzid);
    }



}
