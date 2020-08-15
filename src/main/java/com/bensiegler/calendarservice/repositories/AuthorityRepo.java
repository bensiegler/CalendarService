package com.bensiegler.calendarservice.repositories;

import com.bensiegler.calendarservice.models.dbmodels.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, UUID> {

    Authority findByAuthorityGrantedToAndCalendarObjectId(UUID userId, UUID calendarObjectId);

}
