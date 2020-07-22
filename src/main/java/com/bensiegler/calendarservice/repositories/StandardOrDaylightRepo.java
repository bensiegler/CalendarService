package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.StandardOrDaylight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardOrDaylightRepo extends JpaRepository<StandardOrDaylight, Integer> {

}
