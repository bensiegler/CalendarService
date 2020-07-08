package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PropertyParameterRepo extends JpaRepository<DBParameter, Long> {
    ArrayList<DBParameter> findByCalendarId(Long calendarId);
}
