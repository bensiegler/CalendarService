package com.bensiegler.calendarservice.repositories.tz;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.StandardOrDaylight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardOrDaylightRepo extends JpaRepository<StandardOrDaylight, Integer> {

}
