package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface EventPropertyRepo extends JpaRepository<DBProperty, String> {
    ArrayList<DBProperty> findByCalendarId(Long calendarId);

}
