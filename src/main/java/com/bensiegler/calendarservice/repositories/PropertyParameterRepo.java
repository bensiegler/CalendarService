package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyParameterRepo extends JpaRepository<DBParameter, Long> {
}
