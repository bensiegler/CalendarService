package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EventRepo extends JpaRepository<DBEvent, Long> {
    ArrayList<DBEvent> findByCalendarId(Long calendarID);
}
