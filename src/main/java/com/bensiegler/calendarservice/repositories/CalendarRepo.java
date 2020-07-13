package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBEventStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepo extends JpaRepository<DBEventStore, Long> {
    DBEventStore findByOwnerId(Long ownerId);

    DBEventStore findByName(String name);
}
