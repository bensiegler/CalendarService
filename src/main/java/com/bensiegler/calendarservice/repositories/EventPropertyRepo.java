package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodel.DBProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPropertyRepo extends JpaRepository<DBProperty, Long> {
}
