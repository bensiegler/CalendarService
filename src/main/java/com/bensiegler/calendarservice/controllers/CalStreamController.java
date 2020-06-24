package com.bensiegler.calendarservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class CalStreamController {

    @GetMapping()
    public String getCalStream() {
        System.out.println("cal requested");
        String calStream = "";
        try (BufferedReader inputReader = new BufferedReader(
                new FileReader("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/64969478593"))) {

            String line;
            while ((line = inputReader.readLine()) != null ) {
                calStream += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calStream;
    }


}
