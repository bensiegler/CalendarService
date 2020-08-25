package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.Authority;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, String> {

    List<Authority> findByAuthorityGrantedToAndCalendarObjectId(String userId, String calendarObjectId);
    List<Authority> findByAuthorityGrantedToAndPowerGiven(String userId, String powerGiven);
    List<Authority> findByCalendarObjectId(String calendarObjectId);

    Authority findByPowerGivenAndAuthorityGrantedToAndCalendarObjectId(String powerGiven, String authorityGrantedTo, String calendarObjectId);

}
