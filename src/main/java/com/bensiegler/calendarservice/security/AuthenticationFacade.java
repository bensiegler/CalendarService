package com.bensiegler.calendarservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Configuration
public class AuthenticationFacade {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserId() {
        UserDetails details = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details.getUsername();
    }
}
