package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.exceptions.AuthorityException;
import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.repositories.AuthorityRepo;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepo authorityRepo;


    public List<Authority> getByCalendarObjectIdAndUserId(String calendarObjectId) {
        String userId = AuthenticationFacade.getUserId();
        List<Authority> authorities =  authorityRepo.findByAuthorityGrantedToAndCalendarObjectId(userId, calendarObjectId);

        for(Authority authority: authorities) {
            authority.formatUUIDs();
        }

        return authorities;
    }

    public Authority getByCalendarObjectIdAndPowerGivenAndUserId(String calendarObjectId, String powerGiven) {
        return authorityRepo.findByPowerGivenAndAuthorityGrantedToAndCalendarObjectId(powerGiven,
                AuthenticationFacade.getUserId(), calendarObjectId);
    }

    public List<Authority> getByAuthorityGrantedToAndPowerGiven(String powerGiven) {
        return authorityRepo.findByAuthorityGrantedToAndPowerGiven(AuthenticationFacade.getUserId(), powerGiven);
    }

    public void deleteAllAuthoritiesForAnObject(String calendarObjectId) {
        ArrayList<Authority> authoritiesToBeDeleted = (ArrayList<Authority>)authorityRepo.findByCalendarObjectId(calendarObjectId);

        authorityRepo.delete(authoritiesToBeDeleted);
    }


    public Authority grantAuthority(Authority authority) throws AuthorityException {
        String userId = AuthenticationFacade.getUserId();

        checkAuthorityNotAlreadyGiven(authority.getPowerGiven(), authority.getAuthorityGrantedTo(), authority.getCalendarObjectId());

        Authority newPowers = new Authority();
        newPowers.setActionTakenBy(userId);
        newPowers.setAuthorityGrantedTo(authority.getAuthorityGrantedTo());
        newPowers.setCalendarObjectId(authority.getCalendarObjectId());
        newPowers.setPowerGiven(authority.getPowerGiven());
        newPowers.formatUUIDs();
        authorityRepo.save(newPowers);

        return newPowers;
    }

    public void revokeAuthority(Authority authority) throws AuthorityException {
        authority.formatUUIDs();

        Authority dbAuthority = authorityRepo.findOne(authority.getId());

        //TODO problem since sharers can remove owners authorities which should not be possible
        if(dbAuthority.getAuthorityGrantedTo().equals(dbAuthority.getActionTakenBy()) || dbAuthority.getPowerGiven().equals("OWNER")) {
            throw new AuthorityException("You are the owner of this and cannot revoke your own authorities");
        }

        if(null != authority.getId()) {
            authorityRepo.delete(authority.getId());
        }else{
            authorityRepo.delete(authorityRepo.findByPowerGivenAndAuthorityGrantedToAndCalendarObjectId(authority.getPowerGiven(),
                    authority.getAuthorityGrantedTo(), authority.getCalendarObjectId()).getId());
        }
    }

    private void checkAuthorityNotAlreadyGiven(String powerGiven, String givingPowerTo, String calendarObjectId) throws AuthorityException{
        Authority authority = authorityRepo.findByPowerGivenAndAuthorityGrantedToAndCalendarObjectId(powerGiven, givingPowerTo, calendarObjectId);
        if(authority != null) {
            throw new AuthorityException("This user has already been granted that authority on that object");
        }
    }

    public void addAuthorityToPrincipal(String authority) {
        Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)AuthenticationFacade.getAuthentication().getAuthorities();
        SimpleGrantedAuthority newAuthority = new SimpleGrantedAuthority(authority);
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(newAuthority);
        updatedAuthorities.addAll(oldAuthorities);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                updatedAuthorities
        ));
    }




}
