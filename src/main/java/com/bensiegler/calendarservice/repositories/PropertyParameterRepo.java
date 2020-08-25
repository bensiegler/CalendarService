package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface PropertyParameterRepo extends JpaRepository<DBParameter, String> {
    ArrayList<DBParameter> findByCalendarId(String calendarId);
    ArrayList<DBParameter> findByEventId(String eventId);
}
