package com.bensiegler.calendarservice.controllers;

import com.bensiegler.NumberGenerators;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.services.calstream.CalendarStreamService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class CalStreamController {

    @Autowired
    CalendarStreamService streamService;


    @GetMapping("/calStream")
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
