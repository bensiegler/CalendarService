package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface EventRepo extends JpaRepository<DBEvent, String> {
    ArrayList<DBEvent> findByCalendarId(Long calendarID);
}
