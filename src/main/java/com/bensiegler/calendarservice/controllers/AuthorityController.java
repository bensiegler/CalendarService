package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.exceptions.AuthorityException;
import com.bensiegler.calendarservice.models.dbmodels.Authority;
import com.bensiegler.calendarservice.repositories.AuthorityRepo;
import com.bensiegler.calendarservice.security.AuthenticationFacade;
import com.bensiegler.calendarservice.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/cal/{calendarObjectId}")
    public List<Authority> getAuthorityByCalendarObjectId(@PathVariable(name = "calendarObjectId") String calendarObjectId) {
        return authorityService.getAuthorityByCalendarObjectId(calendarObjectId);
    }

    @GetMapping("/auths")
    public Collection<? extends GrantedAuthority> authorities() {
        return AuthenticationFacade.getAuthentication().getAuthorities();
    }

    @GetMapping("/all")
    public List<Authority> getAll() {
        ArrayList<Authority> authorities = (ArrayList<Authority>) authorityRepo.findAll();

        for(Authority authority: authorities) {
            authority.formatUUIDs();
        }

        return authorities;
    }

    @PostMapping("/grant")
    public Authority allowView(@RequestBody Authority authority) throws AuthorityException {
       return authorityService.grantAuthority(authority);
    }

    @DeleteMapping("/revoke")
    public void revokeView(@RequestBody Authority authority) {
        authorityService.revokeAuthority(authority);
    }









}
