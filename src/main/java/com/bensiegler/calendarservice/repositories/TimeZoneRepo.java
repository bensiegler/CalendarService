package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

@Component
public interface TimeZoneRepo extends JpaRepository<TimeZone, String> {
//    @Modifying
//    @Query("DELETE FROM ")
//    void deleteByTZID(String tzid);
}
