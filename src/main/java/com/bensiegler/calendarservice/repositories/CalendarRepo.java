package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CalendarRepo extends JpaRepository<DBCalendar, Long> {
    ArrayList<DBCalendar> findByOwnerId(Long ownerId);

    DBCalendar findByName(String name);
}
