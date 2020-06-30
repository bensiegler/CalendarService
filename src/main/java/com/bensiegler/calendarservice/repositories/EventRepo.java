package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface EventRepo extends JpaRepository<DBEvent, Long> {
    ArrayList<DBEvent> findByCalendarId(Long calendarID);
}
