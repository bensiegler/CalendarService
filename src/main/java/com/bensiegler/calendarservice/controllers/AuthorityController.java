package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.AuthorityException;
import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.repositories.AuthorityRepo;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import com.bensiegler.calendarservice.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    @Autowired
    AuthorityRepo authorityRepo;

    @PreAuthorize("isGrantedViewAuthority(#calendarObjectId)")
    @GetMapping("/{calendarObjectId}/cal")
    public List<Authority> getAuthorityByCalendarObjectId(@PathVariable(name = "calendarObjectId") String calendarObjectId) {
        return authorityService.getByCalendarObjectIdAndUserId(calendarObjectId);
    }


    @GetMapping("/auths")
    public Collection<? extends GrantedAuthority> getOwnAuthorities() {
        return AuthenticationFacade.getAuthentication().getAuthorities();
    }

    ////REMOVE////
    @GetMapping("/all")
    public List<Authority> getAll() {
        ArrayList<Authority> authorities = (ArrayList<Authority>) authorityRepo.findAll();

        for(Authority authority: authorities) {
            authority.formatUUIDs();
        }

        return authorities;
    }

    @PreAuthorize("isGrantedShareAuthority(#authority.calendarObjectId)")
    @PostMapping("/grant")
    public Authority grantAuthority(@RequestBody Authority authority) throws AuthorityException {
       return authorityService.grantAuthority(authority);
    }

    @PreAuthorize("isGrantedShareAuthority(#authority.calendarObjectId)")
    @DeleteMapping("/revoke")
    public void revokeAuthority(@RequestBody Authority authority) throws AuthorityException {
        authorityService.revokeAuthority(authority);
    }

}
