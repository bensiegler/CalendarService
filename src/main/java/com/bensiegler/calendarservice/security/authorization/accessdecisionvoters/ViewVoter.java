package com.bensiegler.calendarservice.security.authorization.accessdecisionvoters;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;

public class ViewVoter implements AccessDecisionVoter<CalendarObject> {

    @Autowired
    AuthorityService authorityService;

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
            try {
                //get user ID
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                //get user authorities
                Stream<?> authorities = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority);

                if (authorities.filter(r -> r.equals("VIEW_"
                        + object.getProductIdentifier().retrieveContentAsString() + "_"
                        + userDetails.getUsername())).count() <= 1) {

                    Authority deleteAuthority = authorityService.getAuthorityByCalendarObjectIdAndPowerGiven
                            (
                                    object.getProductIdentifier().retrieveContentAsString(),
                                    "VIEW"
                            );

                    if (null == deleteAuthority) {
                        return ACCESS_DENIED;
                    } else {
                        authorityService.addAuthorityToPrincipal(deleteAuthority.getAuthorityString());
                        return ACCESS_GRANTED;
                    }
                } else {
                    return ACCESS_GRANTED;
                }
            } catch (Exception e) {
                return ACCESS_ABSTAIN;
            }
        } catch (Exception e) {
            return ACCESS_ABSTAIN;
        }
    }
}
