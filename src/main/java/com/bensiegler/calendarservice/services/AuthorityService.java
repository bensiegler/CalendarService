package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.repositories.AuthorityRepo;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepo authorityRepo;

    public Authority getAuthorityByCalendarObjectId(UUID calendarObjectId) {
        UUID  userId = (UUID) AuthenticationFacade.getUserId();
        return authorityRepo.findByAuthorityGrantedToAndCalendarObjectId(userId, calendarObjectId);
    }


}
