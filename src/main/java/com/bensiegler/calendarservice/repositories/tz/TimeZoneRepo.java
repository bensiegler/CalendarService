package com.bensiegler.calendarservice.repositories.tz;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

public interface TimeZoneRepo extends JpaRepository<TimeZone, Integer> {

//    ArrayList<TimeZone> findBy
}
