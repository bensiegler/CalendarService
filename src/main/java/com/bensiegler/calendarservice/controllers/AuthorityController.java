package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    public Authority getAuthorityByCalendarObjectId(UUID calendarObjectID) {
        return authorityService.getAuthorityByCalendarObjectId(calendarObjectID);
    }

}
