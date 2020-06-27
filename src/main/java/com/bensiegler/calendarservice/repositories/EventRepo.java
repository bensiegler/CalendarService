package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodel.DBEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<DBEvent, Long> {
}
