package com.bensiegler.calendarservice.controllers;

import com.bensiegler.calendarservice.models.dbmodels.DBUser;
import com.bensiegler.calendarservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public DBUser getUserByUsername(@PathVariable(name = "username") String username) {
        return userService.getUserByUsername(username);
    }


}
