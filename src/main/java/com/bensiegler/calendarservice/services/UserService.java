package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.models.dbmodels.DBUser;
import com.bensiegler.calendarservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepo userRepo;


    public DBUser getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }


}
