package com.bensiegler.calendarservice.security.authentication;


import com.bensiegler.calendarservice.models.dbmodels.DBUser;
import com.bensiegler.calendarservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsernamePasswordAuthTokenWithID implements AuthenticationProvider {

    @Autowired
    UserRepo userRepo;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = (String)authentication.getPrincipal();
       String password = authentication.getCredentials().toString().trim();
       boolean isAuthenticated = false;

       //pull user from db
        DBUser user = userRepo.findByUsername(username);

       //confirm password
       if(!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Those credentials are invalid");
       }

        return new UsernamePasswordAuthenticationToken(user, password, new ArrayList<>());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
