package com.bensiegler.calendarservice.security.authorization.accessdecisionvoters;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;

public class EditVoter implements AccessDecisionVoter<CalendarObject> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, CalendarObject object, Collection<ConfigAttribute> attributes) {
        try {
            //get user ID
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //get user authorities
            Stream<?> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority);

            if (authorities.filter(r -> r.equals("EDIT_"
                    + object.getProductIdentifier().retrieveContentAsString() + "_"
                    + userDetails.getUsername())).count() <= 1) {

                return ACCESS_DENIED;
            } else {
                return ACCESS_GRANTED;
            }
        } catch (Exception e) {
            return ACCESS_ABSTAIN;
        }
    }
}
