package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EventPropertyRepo extends JpaRepository<DBProperty, Long> {
    ArrayList<DBProperty> findByCalendarId(Long calendarId);

}
