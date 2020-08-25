package com.bensiegler.calendarservice.security.authorization.methodSecurity;

import com.bensiegler.calendarservice.Application;
import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.services.AuthorityService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Configuration
@Component
public class CustomMethodSecurityExpressionDefinitions extends SecurityExpressionRoot implements  MethodSecurityExpressionOperations, ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public CustomMethodSecurityExpressionDefinitions(Authentication authentication) {
        super(authentication);
    }

    public boolean isGrantedViewAuthority(String calendarObjectId) {
        AuthorityService authorityService = context.getBean(AuthorityService.class);
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //get user authorities
            Stream<?> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority);

            if (authorities.filter(r -> r.equals("VIEW_"
                + calendarObjectId + "_"
                + userDetails.getUsername())).count() <= 1) {

            Authority possibleViewAuthority = authorityService.getByCalendarObjectIdAndPowerGivenAndUserId
                    (
                            calendarObjectId,
                            "VIEW"
                    );

                if(null == possibleViewAuthority) {
                    return false;
                }else {
                    authorityService.addAuthorityToPrincipal(possibleViewAuthority.getAuthorityString());
                    return true;
                }
            } else {
                return true;
            }
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isGrantedEditAuthority(String calendarObjectId) {
        AuthorityService authorityService = context.getBean(AuthorityService.class);
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //get user authorities
            Stream<?> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority);

            if (authorities.filter(r -> r.equals("EDIT_"
                    + calendarObjectId + "_"
                    + userDetails.getUsername())).count() <= 1) {

                Authority possibleEditAuthority = authorityService.getByCalendarObjectIdAndPowerGivenAndUserId
                        (
                                calendarObjectId,
                                "EDIT"
                        );

                if(null == possibleEditAuthority) {
                    return false;
                }else {
                    authorityService.addAuthorityToPrincipal(possibleEditAuthority.getAuthorityString());
                    return true;
                }
            } else {
                return true;
            }
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isGrantedShareAuthority(String calendarObjectId) {
        AuthorityService authorityService = context.getBean(AuthorityService.class);
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //get user authorities
            Stream<?> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority);

            if (authorities.filter(r -> r.equals("SHARE_"
                    + calendarObjectId + "_"
                    + userDetails.getUsername())).count() <= 1) {

                Authority possibleShareAuthority = authorityService.getByCalendarObjectIdAndPowerGivenAndUserId
                        (
                                calendarObjectId,
                                "SHARE"
                        );

                if(null == possibleShareAuthority) {
                    return false;
                }else {
                    authorityService.addAuthorityToPrincipal(possibleShareAuthority.getAuthorityString());
                    return true;
                }
            } else {
                return true;
            }
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isGrantedDeleteAuthority(String calendarObjectId) {
        AuthorityService authorityService = context.getBean(AuthorityService.class);
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //get user authorities
            Stream<?> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority);

            if (authorities.filter(r -> r.equals("DELETE_"
                    + calendarObjectId + "_"
                    + userDetails.getUsername())).count() <= 1) {

                Authority possibleDeleteAuthority = authorityService.getByCalendarObjectIdAndPowerGivenAndUserId
                        (
                                calendarObjectId,
                                "DELETE"
                        );

                if(null == possibleDeleteAuthority) {
                    return false;
                }else {
                    authorityService.addAuthorityToPrincipal(possibleDeleteAuthority.getAuthorityString());
                    return true;
                }
            } else {
                return true;
            }
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isGrantedOwnerAuthority(String calendarObjectId) {
        AuthorityService authorityService = context.getBean(AuthorityService.class);
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //get user authorities
            Stream<?> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority);

            if (authorities.filter(r -> r.equals("OWNER_"
                    + calendarObjectId + "_"
                    + userDetails.getUsername())).count() <= 1) {

                Authority possibleOwnerAuthority = authorityService.getByCalendarObjectIdAndPowerGivenAndUserId
                        (
                                calendarObjectId,
                                "OWNER"
                        );

                if(null == possibleOwnerAuthority) {
                    return false;
                }else {
                    authorityService.addAuthorityToPrincipal(possibleOwnerAuthority.getAuthorityString());
                    return true;
                }
            } else {
                return true;
            }
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
