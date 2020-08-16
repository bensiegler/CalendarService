package com.bensiegler.calendarservice.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UsernamePasswordAuthTokenWithID token;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return token.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if (authentication.equals(UsernamePasswordAuthTokenWithID.class)) {
            return true;
        } else if (authentication.equals(UsernamePasswordAuthenticationToken.class)) {
            return true;
        } else {
            return false;
        }
    }
}
