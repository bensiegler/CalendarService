package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodel.DBCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepo extends JpaRepository<DBCalendar, Long> {
    DBCalendar findByOwnerId(Long ownerId);
}
