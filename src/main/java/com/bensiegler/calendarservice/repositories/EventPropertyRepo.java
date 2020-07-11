package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface EventPropertyRepo extends JpaRepository<DBProperty, Long> {
    ArrayList<DBProperty> findByCalendarId(Long calendarId);

}
